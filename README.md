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
docker run -d --name surf-observation-api -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/surf_observation_db -e SPRING_DATASOURCE_USERNAME=postgres -e SPRING_DATASOURCE_PASSWORD=postgres123 surf-observation-api:1.0
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

# API Screenshots

## Register Request
<img width="3840" height="2160" alt="Register" src="https://github.com/user-attachments/assets/42c6ae88-1d35-4945-ae3c-83f438259d56" />

## Login Request
<img width="3840" height="2160" alt="Login" src="https://github.com/user-attachments/assets/ec922bcd-58d2-43eb-87af-4c927af585e3" />


## Create Observation
<img width="3840" height="2160" alt="Add Observation" src="https://github.com/user-attachments/assets/dda6b160-f787-43b5-b787-61ff1f174524" />


## Get Observations
<img width="3840" height="2160" alt="Get Observations" src="https://github.com/user-attachments/assets/b09eb636-79b5-4ad4-ae47-a069c02fd95b" />

## Delete Observations
<img width="3840" height="2160" alt="Delete Observations" src="https://github.com/user-attachments/assets/11353730-e55b-4062-b81f-d4c34aa47fef" />
