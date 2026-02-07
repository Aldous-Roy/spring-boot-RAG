package com.aldous_roy.ragsystem.service;

import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileReaderService {

    public List<String> readFile() {
        try {
            return Files.readAllLines(Paths.get("src/main/resources/data.txt"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read file", e);
        }
    }
}