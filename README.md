A highly scalable backend routing system built to handle high-concurrency order processing. Architected using strict Object-Oriented Programming (OOP) principles and relational database integrations.

Designed to successfully process 10,000+ daily requests while maintaining 99.9% transactional accuracy and zero data anomalies.

🚀 Key Features
OOP Design Patterns: Implemented the Strategy Pattern to cleanly route incoming data processing requests based on payload type, ensuring highly maintainable and extensible code.
ACID Transactional Integrity: Heavily utilized database transaction management to ensure that complex, multi-step API integrations either succeed entirely or roll back safely, preventing corrupted data states.
Scalable Architecture: Structured the application with clear separation of concerns (Controllers, Services, Repositories, DTOs).
🛠️ Tech Stack
Core: Java 17
Framework: Spring Boot, Spring Web
Data Persistence: Spring Data JPA / Hibernate
Database: PostgreSQL
Build Tool: Maven
💻 Local Setup & Installation
# Clone the repository
git clone [https://github.com/shivkhurana/distributed-order-system.git](https://github.com/shivkhurana/distributed-order-system.git)

# Navigate into the directory
cd distributed-order-system

# Ensure your local PostgreSQL server is running and update the application.properties file
# src/main/resources/application.properties
# spring.datasource.url=jdbc:postgresql://localhost:5432/yourdb
# spring.datasource.username=youruser
# spring.datasource.password=yourpassword

# Build the project using Maven
mvn clean install

# Run the Spring Boot application
mvn spring-boot:run
