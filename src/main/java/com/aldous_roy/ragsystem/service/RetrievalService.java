package com.aldous_roy.ragsystem.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RetrievalService {

    public List<String> retrieveRelevantChunks(List<String> lines, String question) {
        List<String> relevant = new ArrayList<>();

        for (String line : lines) {
            if (line.toLowerCase().contains(question.toLowerCase())) {
                relevant.add(line);
            }
        }

        return relevant.isEmpty() ? lines.subList(0, Math.min(2, lines.size())) : relevant;
    }
}
