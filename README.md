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

Security/JWT Configuration
- SECURITY_JWT_SECRET: Base64-encoded secret used for signing JWTs (required in prod)
- SECURITY_JWT_EXPIRATION_SECONDS: Token validity period in seconds (default: 36000)

Supabase attachment handling
- Frontend should upload claim files to Supabase Storage and pass the resulting public URL as attachmentUrl when submitting a claim.
- Backend stores this URL as an audit trail entry (action=ATTACHMENT_ADDED) against the Claim entity for traceability without schema changes.

API Highlights
- Policies: POST /api/policies (ADMIN), POST /api/policies/{id}/premium (ADMIN), GET /api/policies, GET /api/policies/type/{type}
- Customer Policies: POST /api/customer-policies/purchase, POST /api/customer-policies/{id}/cancel, GET /api/customer-policies/customer/{customerId}
- Claims: POST /api/claims, POST /api/claims/{id}/status (AGENT/ADMIN), GET /api/claims/ref/{reference}, GET /api/claims/customer/{customerId}
- Payments: POST /api/payments/premium, POST /api/payments/payout (AGENT/ADMIN), GET /api/payments/customer/{customerId}
- Reports: GET /api/reports/claims (ADMIN), GET /api/reports/premiums (ADMIN)

Quick test
1) Register a user:
   curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d '{"fullName":"Alice","email":"alice@example.com","password":"secret","roles":["ADMIN"]}'
2) Use token to call protected endpoint:
   curl http://localhost:8080/api/admin/roles -H "Authorization: Bearer <token>"
3) Swagger UI:
   http://localhost:8080/swagger-ui.html