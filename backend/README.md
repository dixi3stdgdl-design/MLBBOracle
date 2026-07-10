# MLBB Oracle Backend

Fastify-based REST API for storing and retrieving MLBB match data, hero analytics, and overlay configuration.

## Quick Start

```bash
npm install
npm start
```

Server runs on `http://localhost:3005`.

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `PORT` | `3005` | Server port |
| `DATABASE_PATH` | `./data/oracle.db` | SQLite database path |

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/health` | Health check |
| GET | `/api/matches` | List matches |
| POST | `/api/matches` | Create match |
| GET | `/api/matches/:id` | Match details |
| GET | `/api/heroes` | Hero stats |
| GET | `/api/overlay/config` | Overlay configuration |
| POST | `/api/overlay/config` | Update overlay config |

## Tech Stack

- Fastify 4
- better-sqlite3
- WebSocket (@fastify/websocket)

## Docker

```bash
docker build -t mlbb-oracle-backend .
docker run -p 3005:3005 mlbb-oracle-backend
```
