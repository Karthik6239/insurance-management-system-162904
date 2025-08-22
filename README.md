# Project Repository

This repository contains a fullstack Insurance Management System.

Backend (Spring Boot) - Database Configuration
- The backend now uses MySQL via Spring Data JPA. H2 has been removed.
- Configuration is externalized via environment variables and Spring profiles.

Spring Profiles
- Default profile: dev (if SPRING_PROFILES_ACTIVE is not set)
- dev: spring.jpa.hibernate.ddl-auto=update (auto-migrates for local development)
- prod: spring.jpa.hibernate.ddl-auto=validate (no schema changes; ensure schema is present)

Environment Variables
- SPRING_PROFILES_ACTIVE: dev | prod
- MYSQL_URL: full JDBC URL to your MySQL instance
- MYSQL_USER: username
- MYSQL_PASSWORD: password
- MYSQL_DB: database name (used in dev for default URL composition)
- MYSQL_PORT: port (used in dev for default URL composition)

Quick start (dev)
1) Copy .env.example to .env and adjust as needed.
2) Ensure a MySQL server is running locally and accessible.
3) Start the backend:
   - Linux/macOS: SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun
   - Windows (PowerShell): $env:SPRING_PROFILES_ACTIVE="dev"; ./gradlew.bat bootRun

Production notes
- Set SPRING_PROFILES_ACTIVE=prod and provide MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD explicitly.
- Schema updates should be handled via migrations or DBA-managed scripts prior to deployment.