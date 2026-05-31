# Distributed Order Routing System

Spring Boot-based distributed backend architecture for order processing and routing.

Features:
- Strategy-based order routing for `STANDARD`, `EXPRESS`, and `BULK` order types
- External API integration via `WebClient`
- JPA entity modeling with transactional order processing
- Centralized exception handling with `@ControllerAdvice`

## Run

mvn spring-boot:run
