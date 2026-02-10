package com.aldous_roy.ragsystem.controller;

import com.aldous_roy.ragsystem.service.FileReaderService;
import com.aldous_roy.ragsystem.service.OllamaService;
import com.aldous_roy.ragsystem.service.RetrievalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ask")
public class RagController {

    private final FileReaderService fileReaderService;
    private final RetrievalService retrievalService;
    private final OllamaService ollamaService;

    public RagController(FileReaderService fileReaderService,
                         RetrievalService retrievalService,
                         OllamaService ollamaService) {
        this.fileReaderService = fileReaderService;
        this.retrievalService = retrievalService;
        this.ollamaService = ollamaService;
    }

    @GetMapping
    public String checkout() {
        return "The server is working";
    }

    @PostMapping
    public String ask(@RequestBody String question) throws Exception {

        List<String> lines = fileReaderService.readFile();
        List<String> relevant =
                retrievalService.retrieveRelevantChunks(lines, question);

        String context = String.join("\n", relevant);

        return ollamaService.ask(context, question);
    }
}