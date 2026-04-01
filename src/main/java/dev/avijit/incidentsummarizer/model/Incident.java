package dev.avijit.incidentsummarizer.model;

import java.util.List;

public record Incident(
        String source,
        String title,
        List<Alert> alerts,
        List<String> logLines,
        List<String> chatMessages
) {}
