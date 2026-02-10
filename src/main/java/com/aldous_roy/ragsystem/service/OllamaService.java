package com.aldous_roy.ragsystem.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OllamaService {

    private static final String OLLAMA_URL =
            "http://127.0.0.1:11434/api/generate";

    private final RestTemplate restTemplate = new RestTemplate();

    public String ask(String context, String question) {

        String prompt = """
        You are a helpful assistant.
        Answer ONLY using the context below.
        If the answer is not present, say "I don't know".

        Context:
        %s

        Question:
        %s
        """.formatted(context, question);

        Map<String, Object> body = Map.of(
                "model", "gemma3:1b",
                "prompt", prompt,
                "stream", false
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(
                        OLLAMA_URL,
                        request,
                        Map.class
                );

        return response.getBody().get("response").toString();
    }
}