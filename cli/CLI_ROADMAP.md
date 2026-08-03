# Go CLI — `incident-cli` Roadmap & Vision

## What it is

`incident-cli` is the operator-facing layer for the incident-summarizer service — a Go-based terminal tool that lets on-call engineers interact with the summarizer without crafting raw `curl` commands or opening a browser.

The relationship mirrors `kubectl` → Kubernetes API server: the CLI is the human interface, the Spring Boot service is the brain.

**Why Go?** Idiomatic SRE tooling is written in Go (kubectl, Terraform, Prometheus, ArgoCD). Building this in Go gives the project a polyglot story and demonstrates real platform engineering thinking.

---

## Commands

| Command | Endpoint | What it does |
|---|---|---|
| `incident-cli summarize` | `POST /api/v1/incidents/summarize` | Ingest a JSON file or stdin, render structured RCA in terminal |
| `incident-cli history` | `GET /api/v1/incidents` | Paginated table of past incidents |
| `incident-cli get <id>` | `GET /api/v1/incidents/{id}` | Full RCA for a single incident |

All commands support `--format table|json` for terminal rendering vs piping into other tools.

---

## Repo structure

The CLI lives inside the main repo as a subdirectory — one repo, one README, one architecture diagram, one demo gif.

```
incident-summarizer/
├── src/main/java/...        ← Spring Boot service
├── cli/                     ← Go CLI
│   ├── cmd/
│   │   └── incident-cli/
│   │       └── main.go
│   ├── internal/
│   │   ├── client/          ← HTTP client for the Spring Boot service
│   │   └── render/          ← Renderer interface: table + JSON implementations
│   ├── go.mod
│   ├── go.sum
│   └── Makefile
└── helm/                    ← Helm chart (future)
```

---

## Milestones

### Now — parallel to v0.2.0 (scaffold only, ~half a day)
- [ ] Create `cli/` directory structure as above
- [ ] Initialise `go.mod` (`module github.com/Avijit-07/incident-summarizer/cli`)
- [ ] Add Cobra dependency (`go get github.com/spf13/cobra`)
- [ ] Stub `main.go` with root command and three empty subcommands
- [ ] Add `Makefile` with `build`, `run`, `cross-compile` targets
- [ ] Update repo README to mention the CLI exists

**Goal:** `go build ./...` passes. Nothing functional yet — just the skeleton.

### v0.3.0 — implement `summarize` command
- [ ] `internal/client/` — typed HTTP client wrapping `net/http` with configurable base URL + timeout
- [ ] `internal/render/` — `Renderer` interface with `TableRenderer` and `JSONRenderer` implementations
- [ ] `summarize` command: reads JSON file (or stdin via `-`), calls client, renders RCA
- [ ] Loading spinner via goroutine + channel while Claude API is thinking
- [ ] `--format` flag (`pretty` default, `json` for piping)
- [ ] Proper stderr vs stdout separation; non-zero exit codes on failure

**Go concepts naturally learned:** `encoding/json`, `net/http` client, interfaces, goroutines + channels, error-as-value pattern.

### v0.4.0 — `history` and `get` commands
- [ ] `history` command: calls paginated GET, renders `text/tabwriter` table (ID, title, severity, timestamp)
- [ ] `get <id>` command: fetches and renders full summary for one incident
- [ ] `--page` and `--limit` flags on `history`

**Go concepts naturally learned:** `text/tabwriter`, flag parsing, pagination handling.

### v0.6.0 — production hardening
- [ ] Config file support: `~/.incident-cli/config.yaml` for base URL + auth headers (using `viper`)
- [ ] Context-aware timeouts on all HTTP calls
- [ ] Cross-compile in GitHub Actions: `linux/amd64`, `darwin/amd64`, `darwin/arm64`
- [ ] Attach compiled binaries to GitHub Releases as downloadable artifacts

**Go concepts naturally learned:** `viper` for config, `context.WithTimeout`, cross-compilation (`GOOS`/`GOARCH`).

### v1.0.0 — demo ready
- [ ] CLI section in main README with install instructions (`go install` or download binary)
- [ ] Demo gif: `incident-cli summarize sample-incident.json` producing a formatted RCA
- [ ] The gif is the most compelling thing to show in an interview

---

## Go concepts covered (interview talking points)

| Concept | Where it appears |
|---|---|
| Cobra CLI framework | Command/subcommand structure — same library as `kubectl` |
| `encoding/json` | Request/response marshalling |
| `net/http` client | Calling the Spring Boot service |
| Interfaces | `Renderer` with `TableRenderer` + `JSONRenderer` |
| Goroutines + channels | Terminal spinner during API call |
| Error-as-value pattern | All error handling throughout |
| `text/tabwriter` | Table rendering in `history` |
| `viper` | Config file support |
| Cross-compilation | `GOOS=linux GOARCH=amd64 go build` |
| `os.Exit` codes + stderr | CLI citizenship / scriptability |

---
