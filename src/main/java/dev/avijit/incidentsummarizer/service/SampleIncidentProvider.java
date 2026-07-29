package dev.avijit.incidentsummarizer.service;

import dev.avijit.incidentsummarizer.model.Alert;
import dev.avijit.incidentsummarizer.model.Incident;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SampleIncidentProvider {

  public Incident getSampleIncident() {
    var alerts =
        List.of(
            new Alert(
                Instant.parse("2025-03-15T14:32:00Z"),
                "critical",
                "Error rate exceeded 5% threshold on payment-service"),
            new Alert(
                Instant.parse("2025-03-15T14:33:00Z"),
                "warning",
                "Database connection pool exhausted on payments-db-primary"),
            new Alert(
                Instant.parse("2025-03-15T14:35:00Z"),
                "critical",
                "payment-service: 502 Bad Gateway responses spiking"),
            new Alert(
                Instant.parse("2025-03-15T14:45:00Z"),
                "info",
                "Auto-scaling triggered for payment-service"),
            new Alert(
                Instant.parse("2025-03-15T15:02:00Z"),
                "info",
                "Error rate returned below 1% on payment-service"));

    var logLines =
        List.of(
            "2025-03-15T14:31:45Z payment-service ERROR: HikariPool-1 - Connection is not available, request timed out after 30000ms",
            "2025-03-15T14:31:50Z payment-service ERROR: Unable to acquire JDBC connection",
            "2025-03-15T14:32:10Z payment-service ERROR: Transaction failed: org.springframework.dao.DataAccessResourceFailureException",
            "2025-03-15T14:33:30Z payments-db-primary WARN: max_connections (100) reached",
            "2025-03-15T14:40:00Z payment-service INFO: Deploy v2.14.3 rolled back to v2.14.2",
            "2025-03-15T14:55:00Z payments-db-primary INFO: connections dropped to 45/100");

    var chatMessages =
        List.of(
            "[14:32] @oncall-payments: Getting paged for payment-service error rate. Looking into it.",
            "[14:34] @oncall-payments: Seeing connection pool exhaustion. DB connections maxed out.",
            "[14:36] @backend-lead: Did anything deploy recently?",
            "[14:37] @oncall-payments: Yes — v2.14.3 went out 20 mins ago. Checking changelog.",
            "[14:38] @oncall-payments: v2.14.3 added a new batch query that opens multiple connections per request. That's likely it.",
            "[14:39] @backend-lead: Roll it back.",
            "[14:40] @oncall-payments: Rolling back to v2.14.2 now.",
            "[14:50] @oncall-payments: Rollback complete. Connections stabilizing. Error rate dropping.",
            "[15:03] @oncall-payments: All clear. Error rate back to normal.");

    return new Incident(
        "pagerduty", "High error rate on payment-service", alerts, logLines, chatMessages);
  }
}
