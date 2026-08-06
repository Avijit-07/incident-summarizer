---
title: "Overview"
weight: 1
date: 2026-08-06
showDate: false
---

## The Problem

After an incident, you're staring at a wall of alerts, Slack messages, and monitoring data trying to piece together what happened, when, and why. Writing a good summary or postmortem timeline is tedious and error-prone.

## What This Project Does

Incident Summarizer ingests raw incident data and uses an LLM (Anthropic Claude API) to produce:

- **Structured timeline** of events
- **Impact assessment** with severity classification
- **Suggested root cause** analysis
- **Action items** for follow-up

## Architecture

```
flowchart LR
    A[Raw Input\nJSON / Text] --> B[Incident Processor\nParse & Clean\nBuild Context]
    B --> C[LLM Service\nClaude API]
    C --> D[Summary Builder\nTimeline\nImpact\nRCA Hints]
    D --> E[Incident Store\nH2 / SQLite]
    B --> E
```

## Tech Stack

| Component        | Technology                    |
|-------------------|-------------------------------|
| Language          | Java 25                       |
| Framework         | Spring Boot                   |
| AI Provider       | Anthropic Claude API          |
| Database          | H2 / SQLite                   |
| API Style         | REST (JSON)                   |
| Build Tool        | Gradle                        |
| Frontend (later)  | React + TypeScript (optional) |

For setup instructions, configuration, and the full project roadmap, see the [README on GitHub](https://github.com/Avijit-07/incident-summarizer).
