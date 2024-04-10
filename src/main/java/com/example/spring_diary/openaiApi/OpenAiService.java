package com.example.spring_diary.openaiApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAiService {

    private final RestTemplate restTemplate;

    @Value("${openai.apikey}")
    private String apiKey;

    @Autowired
    public OpenAiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ChatResponse chatCompletions(ChatRequest request) {
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<ChatRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<ChatResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, ChatResponse.class);

        return response.getBody();
    }
}
