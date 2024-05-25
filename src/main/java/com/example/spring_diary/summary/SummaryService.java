package com.example.spring_diary.summary;

import com.example.spring_diary.diary.Diary;
import com.example.spring_diary.diary.DiaryDto;
import com.example.spring_diary.diary.DiaryRepository;
import com.example.spring_diary.openaiApi.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final SummaryClient summaryClient;
    private final static String ROLE_USER = "user";
    private final static String MODEL = "gpt-3.5-turbo";


    @Value("${apikey}")
    private String apiKey;

    @Autowired
    SummaryRepository summaryRepository;

    @Autowired
    DiaryRepository diaryRepository;


//    ChatGPT 이용하여 요약
    public String summarize(DiaryDto diaryDto) {

        Diary diary = diaryRepository.findById(diaryDto.getDiaryId())
                .orElseThrow(() -> new RuntimeException("Diary not found"));

        Message message = Message.builder()
                .role(ROLE_USER)
                .content(diaryDto.getContent()+" ... 이건 내가 쓴 일기야. 일기를 요약하고 내 일기에서 보이는 내 내면을 분석해줘. 다른 말은 붙이지 말고 그냥 요약문과 분석문 자체만 출력해줘.")
                .build();

        ChatRequest chatRequest = ChatRequest.builder()
                .model(MODEL)
                .messages(Collections.singletonList(message))
                .build();

        String bearerToken = "Bearer " + apiKey;

        Summary summary = new Summary(message.getContent(),diary);
        summaryRepository.save(summary);

        return summaryClient
                .summarizeclient(bearerToken,chatRequest)
                .getBody()
                .getChoices()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("summary 클라이언트의 응답 없음"))
                .getMessage()
                .getContent();
    }
}
