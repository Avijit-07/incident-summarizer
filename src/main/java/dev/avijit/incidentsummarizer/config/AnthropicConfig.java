package dev.avijit.incidentsummarizer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "anthropic")
public record AnthropicConfig(
        String apiKey,
        String model,
        int maxTokens
) {}
