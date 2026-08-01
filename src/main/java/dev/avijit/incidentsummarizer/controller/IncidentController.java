package dev.avijit.incidentsummarizer.controller;

import dev.avijit.incidentsummarizer.model.Incident;
import dev.avijit.incidentsummarizer.model.IncidentSummary;
import dev.avijit.incidentsummarizer.service.ClaudeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class IncidentController {

  private ClaudeService service;

  IncidentController(ClaudeService service) {
    this.service = service;
  }

  @PostMapping("/incidents/summarize")
  public IncidentSummary summarize(@RequestBody Incident incident) {
    return service.summarize(incident);
  }
}
