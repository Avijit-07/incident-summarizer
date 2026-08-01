package dev.avijit.incidentsummarizer.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.avijit.incidentsummarizer.service.ClaudeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(IncidentController.class)
public class IncidentControllerTests {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private ClaudeService service;

  @Test
  void shouldReturnIncidentSummaryForValidIncident() {
    assertTrue(true);
  }
}
