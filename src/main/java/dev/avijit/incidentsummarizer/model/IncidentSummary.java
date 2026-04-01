package dev.avijit.incidentsummarizer.model;

public record IncidentSummary(
        String title,
        String summary,
        String timeline,
        String impactAssessment,
        String suggestedRootCause
) {}
