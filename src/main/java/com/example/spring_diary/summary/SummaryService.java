package com.example.spring_diary.summary;

import com.example.spring_diary.diary.Diary;
import com.example.spring_diary.diary.DiaryDto;
import com.example.spring_diary.diary.DiaryRepository;
import com.example.spring_diary.openaiApi.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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

        // ChatGPT API에 요청을 보내고 응답을 처리.
        String summaryContent = summaryClient
                .summarizeclient(bearerToken, chatRequest)
                .getBody()
                .getChoices()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("summary 클라이언트의 응답 없음"))
                .getMessage()
                .getContent();

        // Summary 객체를 생성하고, ChatGPT로부터 받은 내용을 설정
        Summary summary = new Summary(summaryContent, diary);

        // Summary 객체를 데이터베이스에 저장.
        summaryRepository.save(summary);

        // 요약 및 분석 결과를 반환.
        return summaryContent;
    }

    //특정 다이어리의 summary 조회
    public List<Summary> getSummariesByDiaryId(DiaryDto diaryDto) {
        return summaryRepository.findAllByDiaryDiaryId(diaryDto.getDiaryId());
    }



    // summary 삭제
    public void deleteSummary(SummaryDto summaryDto) throws EntityNotFoundException {
        Summary summary = summaryRepository.findBySummaryId(summaryDto.getSummaryId());

        if (!summaryRepository.existsById(summaryDto.getSummaryId())) {
            throw new EntityNotFoundException();
        } else {
            Summary deletingSummary = summaryRepository.findBySummaryId(summaryDto.getSummaryId());
            summaryRepository.delete(deletingSummary);
        }

    }




}
