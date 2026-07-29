package dev.avijit.incidentsummarizer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "anthropic")
public record AnthropicConfig(
    String url, String apiKey, @DefaultValue("claude-sonnet-4-5") String model, int maxTokens) {}
