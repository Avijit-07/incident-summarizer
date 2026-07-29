package dev.avijit.incidentsummarizer.runner;

import dev.avijit.incidentsummarizer.model.IncidentSummary;
import dev.avijit.incidentsummarizer.service.ClaudeService;
import dev.avijit.incidentsummarizer.service.SampleIncidentProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class SummarizeRunner implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(SummarizeRunner.class);

  private final SampleIncidentProvider sampleIncidentProvider;
  private final ClaudeService claudeService;

  public SummarizeRunner(
      SampleIncidentProvider sampleIncidentProvider, ClaudeService claudeService) {
    this.sampleIncidentProvider = sampleIncidentProvider;
    this.claudeService = claudeService;
  }

  @Override
  public void run(String... args) {
    log.info("=== Incident Summarizer — Week 1 Demo ===");

    var incident = sampleIncidentProvider.getSampleIncident();
    log.info("Loaded sample incident: {}", incident.title());
    log.info(
        "  Alerts: {}, Log lines: {}, Chat messages: {}",
        incident.alerts().size(),
        incident.logLines().size(),
        incident.chatMessages().size());

    log.info("Sending to Claude for summarization...");
    IncidentSummary summary = claudeService.summarize(incident);

    log.info("\n\n========== INCIDENT SUMMARY ==========\n");
    log.info("Title: {}", summary.title());
    log.info("\n{}", summary.summary());
    log.info("\n=======================================\n");
  }
}
