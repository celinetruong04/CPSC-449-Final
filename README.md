# CPSC-449-Final
Surf Observation API

---

## Team Members
- Brajan Memishi — CWID: 888447067  
- Celine Truong — CWID: 885375519  

---

## Description
Surf Observation API is a Spring Boot REST API that allows authenticated users to create, manage, update, and delete surf observations. The application uses JWT authentication, PostgreSQL, and Docker.

---

# Prerequisites

Before running the project, install:

- Java 21
- Docker Desktop
- PostgreSQL 18+
- Postman

---

# Database Setup

Create a PostgreSQL database named:

```sql
surf_observation_db
```

```sql
CREATE DATABASE surf_observation_db;
```

---

# Clone Repository

```bash
git clone https://github.com/celinetruong04/CPSC-449-Final
cd CPSC-449-Final
```

---

# Build Docker Image

Run the following command from the project root:

```bash
docker build -t surf-observation-api:1.0 .
```

---

# Run Docker Container

Replace the database password if necessary.

```bash
docker run -d --name surf-observation-api -p 8080:8080 \
-e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/surf_observation_db \
-e SPRING_DATASOURCE_USERNAME=postgres \
-e SPRING_DATASOURCE_PASSWORD=postgres123 \
surf-observation-api:1.0
```

# View Container Logs

```bash
docker logs surf-observation-api
```

---

# API Endpoints

| Method | Endpoint |
|---|---|
| POST | /api/auth/register |
| POST | /api/auth/login |
| POST | /api/observations |
| GET | /api/observations |
| GET | /api/observations/{id} |
| PUT | /api/observations/{id} |
| DELETE | /api/observations/{id} |

---

# Example Postman Requests

## Register User

POST:
```text
http://localhost:8080/api/auth/register
```

Body:
```json
{
  "username": "Brajan",
  "email": "brajan@test.com",
  "password": "password123"
}
```

---

## Create Observation (Protected Endpoint)

POST:
```text
http://localhost:8080/api/observations
```

Authorization:
```text
Bearer Token
```

Body:
```json
{
  "spotName": "Moonlight Beach",
  "waveHeight": 4.5,
  "windSpeed": 8.2,
  "windDirection": "W",
  "tide": "mid",
  "rating": 4,
  "notes": "Clean conditions with decent size.",
  "observationDate": "2026-05-06"
}
```
---
