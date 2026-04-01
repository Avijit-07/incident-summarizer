package dev.avijit.incidentsummarizer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "anthropic.api-key=test-key",
        "anthropic.model=claude-sonnet-4-20250514",
        "anthropic.max-tokens=1024"
})
class IncidentSummarizerApplicationTests {

    @Test
    void contextLoads() {
    }
}
