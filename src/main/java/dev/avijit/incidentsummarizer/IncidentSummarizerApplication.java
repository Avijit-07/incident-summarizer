package dev.avijit.incidentsummarizer;

import dev.avijit.incidentsummarizer.config.AnthropicConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AnthropicConfig.class)
public class IncidentSummarizerApplication {

  public static void main(String[] args) {
    SpringApplication.run(IncidentSummarizerApplication.class, args);
  }
}
