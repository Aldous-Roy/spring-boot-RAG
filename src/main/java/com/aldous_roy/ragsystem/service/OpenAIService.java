package com.aldous_roy.ragsystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    public String askOpenAI(String context, String question) throws Exception {

        Map<String, Object> body = new HashMap<>();
        body.put("model", model);

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", "You are a helpful assistant."));
        messages.add(Map.of("role", "user",
                "content", "Context:\n" + context + "\n\nQuestion:\n" + question));

        body.put("messages", messages);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(body);

        HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
        post.setHeader("Authorization", "Bearer " + apiKey);
        post.setHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity(json));

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            return client.execute(post, response ->
                    mapper.readTree(response.getEntity().getContent())
                            .get("choices")
                            .get(0)
                            .get("message")
                            .get("content")
                            .asText()
            );
        }
    }
}
