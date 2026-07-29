package dev.avijit.incidentsummarizer.service;

import dev.avijit.incidentsummarizer.config.AnthropicConfig;
import dev.avijit.incidentsummarizer.model.Incident;
import dev.avijit.incidentsummarizer.model.IncidentSummary;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class ClaudeService {

  private static final Logger log = LoggerFactory.getLogger(ClaudeService.class);

  private final AnthropicConfig anthropicConfig;
  private final RestClient restClient;
  private final ObjectMapper objectMapper;

  public ClaudeService(AnthropicConfig anthropicConfig, ObjectMapper objectMapper) {
    this.anthropicConfig = anthropicConfig;
    this.objectMapper = objectMapper;
    this.restClient =
        RestClient.builder()
            .baseUrl(anthropicConfig.url())
            .defaultHeader("x-api-key", anthropicConfig.apiKey())
            .defaultHeader("anthropic-version", "2023-06-01")
            .build();
  }

  public IncidentSummary summarize(Incident incident) {
    String prompt = buildPrompt(incident);
    String response = callClaude(prompt);
    return new IncidentSummary(
        incident.title(),
        response,
        "", // structured parsing comes in Week 3
        "",
        "");
  }

  private String callClaude(String userMessage) {
    var requestBody =
        Map.of(
            "model", anthropicConfig.model(),
            "max_tokens", anthropicConfig.maxTokens(),
            "messages", List.of(Map.of("role", "user", "content", userMessage)),
            "system",
                "You are an expert incident analyst. Given raw incident data, produce a clear, "
                    + "structured incident summary including: a timeline of key events, an impact assessment, "
                    + "and a suggested root cause. Be concise and actionable.");

    log.info("Calling Claude API (model: {})", anthropicConfig.model());

    String responseBody =
        restClient
            .post()
            .contentType(MediaType.APPLICATION_JSON)
            .body(requestBody)
            .retrieve()
            .body(String.class);

    return extractTextFromResponse(responseBody);
  }

  private String extractTextFromResponse(String responseBody) {
    try {
      JsonNode root = objectMapper.readTree(responseBody);
      JsonNode content = root.get("content");
      if (content != null && content.isArray() && !content.isEmpty()) {
        return content.get(0).get("text").asText();
      }
      return "No response content";
    } catch (Exception e) {
      log.error("Failed to parse Claude response", e);
      return "Error parsing response: " + e.getMessage();
    }
  }

  private String buildPrompt(Incident incident) {
    var sb = new StringBuilder();
    sb.append("# Incident: ").append(incident.title()).append("\n");
    sb.append("Source: ").append(incident.source()).append("\n\n");

    sb.append("## Alerts\n");
    for (var alert : incident.alerts()) {
      sb.append(
          "- [%s] %s: %s%n"
              .formatted(alert.timestamp(), alert.severity().toUpperCase(), alert.message()));
    }

    if (incident.logLines() != null && !incident.logLines().isEmpty()) {
      sb.append("\n## Log Lines\n");
      sb.append(incident.logLines().stream().map(l -> "  " + l).collect(Collectors.joining("\n")));
    }

    if (incident.chatMessages() != null && !incident.chatMessages().isEmpty()) {
      sb.append("\n\n## Chat / Slack Messages\n");
      sb.append(incident.chatMessages().stream().collect(Collectors.joining("\n")));
    }

    sb.append("\n\nPlease provide:\n");
    sb.append("1. **Timeline** — key events in chronological order\n");
    sb.append("2. **Impact Assessment** — what was affected and severity\n");
    sb.append("3. **Suggested Root Cause** — most likely cause based on the data\n");

    return sb.toString();
  }
}
