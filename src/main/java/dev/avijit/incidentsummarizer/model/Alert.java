package dev.avijit.incidentsummarizer.model;

import java.time.Instant;

public record Alert(
        Instant timestamp,
        String severity,
        String message
) {}
