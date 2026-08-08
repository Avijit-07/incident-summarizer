package dev.avijit.incidentsummarizer.controller;

import dev.avijit.incidentsummarizer.model.Incident;
import dev.avijit.incidentsummarizer.model.IncidentSummary;
import dev.avijit.incidentsummarizer.service.ClaudeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class IncidentController {

  private ClaudeService service;

  public IncidentController(ClaudeService service) {
    this.service = service;
  }

  @PostMapping("/incidents/summarize")
  public IncidentSummary summarize(@RequestBody @Valid @NotNull Incident incident) {
    return service.summarize(incident);
  }
}
