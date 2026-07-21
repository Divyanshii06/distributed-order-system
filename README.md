# Distributed Order Processing System

A highly scalable, multi-tiered backend architecture designed to handle **high-throughput concurrent order requests** in a distributed computing environment.

Built with **Java 17** and **Spring Boot**, the system uses asynchronous message queuing to decouple order ingestion from backend processing, improving **fault tolerance, scalability, availability, and reliability during traffic spikes**.

---

## 🏗️ System Architecture

The system follows a **microservices-inspired, event-driven architecture** where order ingestion and order processing are decoupled through a message queue.

```text
[Client / Load Balancer]
          │
          ▼
[API Gateway / Order Service - Spring Boot]
          │
          ├──► Synchronous Response: "Order Received"
          │
          └──► Publish Order Event
                     │
                     ▼
          [AWS SQS / Message Queue]
                     │
                     └──► Poll Events Asynchronously
                                │
                                ▼
          [Worker Nodes / Processing Service]
                     │
                     └──► Read / Write Operations
                                │
                                ▼
          [AWS RDS - PostgreSQL Database]
```

This architecture allows the API layer to accept incoming orders quickly without waiting for the entire order-processing workflow to complete.

Orders are placed into **AWS SQS**, where worker services asynchronously consume and process them. This design helps the system remain resilient during traffic spikes and temporary downstream failures.

---

## ✨ Key Features

### ⚡ High-Throughput Concurrency

Designed to handle **1,500+ concurrent simulated requests** while preventing long-running order-processing tasks from blocking the main request flow.

### 🛡️ Fault Tolerance & Service Decoupling

Uses **AWS SQS** as a messaging layer to decouple the order ingestion API from backend processing workers.

If a downstream processing service or database becomes temporarily unavailable, pending order messages can remain queued for later processing according to the configured SQS retry and retention policies.

### 📊 Optimized Relational Data

Uses **PostgreSQL** with optimized schema design, indexing strategies, and query analysis to improve database performance.

Performance testing demonstrated approximately **25% lower data retrieval latency** under the tested high-load scenario after query and indexing optimizations.

### 🔁 Idempotent Order Processing

Worker nodes are designed to safely handle duplicate message deliveries.

Idempotency checks help prevent duplicate order creation when the same message is delivered or processed more than once.

### 📦 Containerized Deployment

Uses **Docker** and **Docker Compose** to provide a consistent development and deployment environment across different systems.

---

## 🛠️ Tech Stack

| Category                 | Technology             |
| ------------------------ | ---------------------- |
| **Language**             | Java 17                |
| **Framework**            | Spring Boot            |
| **Persistence**          | Spring Data JPA        |
| **Cloud Infrastructure** | AWS EC2, SQS, RDS      |
| **Database**             | PostgreSQL             |
| **Containerization**     | Docker, Docker Compose |
| **Build Tool**           | Maven                  |
| **API Documentation**    | Swagger / OpenAPI      |

---

## 📂 Project Architecture

```text
distributed-order-system/
│
├── order-service/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── model/
│   └── config/
│
├── processing-service/
│   ├── consumer/
│   ├── service/
│   ├── repository/
│   └── model/
│
├── docker-compose.yml
├── pom.xml
├── .env
└── README.md
```

> The exact directory structure may vary depending on how the services are organized in the implementation.

---

## 🔄 Order Processing Flow

```text
1. Client sends an order request
              │
              ▼
2. Order Service validates the request
              │
              ▼
3. Order event is published to AWS SQS
              │
              ├────► API returns acknowledgment
              │
              ▼
4. Worker Service polls the SQS queue
              │
              ▼
5. Worker processes the order
              │
              ▼
6. Order data is stored/updated in PostgreSQL
              │
              ▼
7. Order processing completes
```

The asynchronous workflow allows the API to acknowledge requests without waiting for all backend processing operations to finish.

---

## 🚀 Local Setup & Deployment

### Prerequisites

Make sure the following tools are installed:

* Java 17+
* Maven
* Docker
* Docker Compose
* PostgreSQL, if running the database locally
* AWS account and configured AWS resources, if using AWS SQS/RDS

---

### 1. Clone the Repository

```bash
git clone https://github.com/Divyanshii06/distributed-order-system.git
cd distributed-order-system
```

---

### 2. Configure Environment Variables

Create a `.env` file in the project root.

```env
AWS_ACCESS_KEY_ID=your_access_key
AWS_SECRET_ACCESS_KEY=your_secret_key
AWS_REGION=us-east-1

DB_URL=jdbc:postgresql://localhost:5432/orders_db
DB_USER=postgres
DB_PASSWORD=postgres
```

> **Security Note:** Never commit `.env`, AWS credentials, database passwords, or other secrets to GitHub.

Add the following entry to `.gitignore`:

```gitignore
.env
```

For production deployments, use secure secret-management mechanisms such as IAM roles or a dedicated secrets manager instead of hardcoding credentials.

---

### 3. Build the Application

```bash
mvn clean install
```

---

### 4. Run with Docker Compose

```bash
docker-compose up -d
```

To check running containers:

```bash
docker ps
```

To stop the application:

```bash
docker-compose down
```

---

## 📖 API Documentation

Once the application is running locally, Swagger UI can be accessed at:

```text
http://localhost:8080/swagger-ui.html
```

Depending on the Springdoc/OpenAPI configuration, the Swagger UI endpoint may also be:

```text
http://localhost:8080/swagger-ui/index.html
```

Swagger provides an interactive interface for exploring and testing the exposed REST API endpoints.

---

## 🧪 Performance Highlights

The architecture is designed and tested around the following performance goals:

| Metric                             | Result                          |
| ---------------------------------- | ------------------------------- |
| Concurrent simulated requests      | **1,500+**                      |
| Data retrieval latency improvement | **~25% in tested workload**     |
| Processing model                   | **Asynchronous / Event-Driven** |
| Message delivery handling          | **Idempotent Processing**       |
| Scaling strategy                   | **Horizontal Worker Scaling**   |

> Performance results depend on infrastructure configuration, dataset size, database configuration, test methodology, and workload characteristics.

---

## 🔐 Reliability Considerations

The system incorporates several distributed-system reliability principles:

* **Asynchronous processing** to prevent long-running backend operations from blocking incoming requests.
* **Message durability** through AWS SQS configuration.
* **Idempotent consumers** to safely handle duplicate message deliveries.
* **Retry mechanisms** for temporary processing failures.
* **Service decoupling** to reduce cascading failures.
* **Database indexing** for efficient high-volume queries.
* **Horizontal scalability** by adding additional worker instances.

For production environments, additional mechanisms such as **Dead Letter Queues (DLQs), monitoring, distributed tracing, rate limiting, and centralized logging** can further improve reliability and observability.

---

## 📈 Scalability

The worker layer can be scaled horizontally as order volume increases.

```text
                    ┌──► Worker Instance 1 ──┐
                    │                        │
[AWS SQS Queue] ────┼──► Worker Instance 2 ──┼──► PostgreSQL / RDS
                    │                        │
                    └──► Worker Instance N ──┘
```

Additional worker instances can consume messages from the queue, allowing processing capacity to scale independently from the API layer.

---

## 🔮 Future Improvements

Potential enhancements include:

* Dead Letter Queue integration for failed messages
* Redis caching for frequently accessed data
* JWT-based authentication and authorization
* Kubernetes deployment and auto-scaling
* AWS CloudWatch monitoring and alerting
* Distributed tracing
* Centralized logging
* CI/CD pipeline using GitHub Actions
* API rate limiting
* Load and stress testing automation

---



This project is intended for educational, portfolio, and demonstration purposes.

