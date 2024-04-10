package com.example.spring_diary.openaiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ChatService {

    private final OpenAiService openAiService;
    private final static String ROLE_USER = "user";
    private final static String MODEL = "gpt-3.5-turbo";

    @Autowired
    public ChatService(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    public String chatCompletions(final String question) {
        Message message = Message.builder()
                .role(ROLE_USER)
                .content(question)
                .build();

        ChatRequest chatRequest = ChatRequest.builder()
                .model(MODEL)
                .messages(Collections.singletonList(message))
                .build();

        ChatResponse response = openAiService.chatCompletions(chatRequest);
        return response.getChoices()
                .stream()
                .findFirst()
                .orElseThrow()
                .getMessage()
                .getContent();
    }
}