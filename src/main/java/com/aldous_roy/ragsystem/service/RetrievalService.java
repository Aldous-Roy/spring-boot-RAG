package com.aldous_roy.ragsystem.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RetrievalService {

    public List<String> retrieveRelevantChunks(List<String> lines, String question) {

        List<String> relevant = new ArrayList<>();

        // Clean and split the question into keywords
        String[] keywords = question
                .toLowerCase()
                .replaceAll("[^a-z0-9 ]", "")
                .split("\\s+");

        for (String line : lines) {
            String lowerLine = line.toLowerCase();

            for (String keyword : keywords) {
                // ignore very small/common words
                if (keyword.length() > 3 && lowerLine.contains(keyword)) {
                    relevant.add(line);
                    break;
                }
            }
        }

        // Fallback: always return some context
        if (relevant.isEmpty()) {
            return lines.subList(0, Math.min(5, lines.size()));
        }

        return relevant;
    }
}