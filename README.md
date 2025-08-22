# Insurance Management System — Backend (Spring Boot)

This backend provides REST APIs for authentication, role and user management, insurance products, customer policies, claims, payments, reporting, and audit logging. It uses MySQL via Spring Data JPA and integrates with Supabase Storage for claim attachments through URLs.

Spring profiles
- Default profile: dev (from spring.profiles.default in application.properties)
- dev: spring.jpa.hibernate.ddl-auto=update for auto schema updates locally
- prod: spring.jpa.hibernate.ddl-auto=validate to prevent automatic schema changes

Environment variables
Database
- MYSQL_URL: Full JDBC URL to MySQL (required in prod; defaults exist for dev)
- MYSQL_USER: Database username
- MYSQL_PASSWORD: Database password
- MYSQL_DB: Database name (used to compose default dev URL)
- MYSQL_PORT: MySQL port (used to compose default dev URL)

Spring/Profiles
- SPRING_PROFILES_ACTIVE: dev | prod

Security/JWT
- SECURITY_JWT_SECRET: Base64-encoded signing key for JWT (required in prod; generate with: openssl rand -base64 32)
- SECURITY_JWT_EXPIRATION_SECONDS: Token validity seconds (default: 36000)

Supabase (server-side, optional)
- SUPABASE_URL: Supabase project URL
- SUPABASE_SERVICE_ROLE_KEY: Service role key for privileged operations
- SUPABASE_BUCKET_CLAIMS: claims-attachments (default convention)

Running locally (dev)
1) Ensure MySQL is running. You can use the provided database container:
   - See insurance-management-system-162905/insurance_management_database/startup.sh to start a local MySQL and obtain connection envs (MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD, MYSQL_DB, MYSQL_PORT).
2) Configure environment variables (shell or IDE run configuration). For example:
   - MYSQL_DB=insurance_db
   - MYSQL_PORT=3306
   - MYSQL_USER=root
   - MYSQL_PASSWORD=password
   - Optionally, set MYSQL_URL to override the composed default.
3) Start the backend:
   - macOS/Linux: SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun
   - Windows (PowerShell): $env:SPRING_PROFILES_ACTIVE="dev"; ./gradlew.bat bootRun
4) Open Swagger UI at:
   - http://localhost:8080/swagger-ui.html

Production notes
- Set SPRING_PROFILES_ACTIVE=prod and provide explicit MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD.
- Provide SECURITY_JWT_SECRET and adjust SECURITY_JWT_EXPIRATION_SECONDS as needed.
- Apply schema changes via migrations or DBA-managed scripts. Do not rely on automatic DDL in prod.

Supabase attachment handling
- Frontend uploads claim files to Supabase Storage (bucket: claims-attachments) and includes attachment metadata in the claim payload as path and signedUrl.
- Backend records attachment events as audit entries for the Claim for traceability. If needed, the backend can also generate signed URLs using SUPABASE_SERVICE_ROLE_KEY.
- See assets/supabase.md for bucket creation, RLS policies, and environment configuration.

API highlights (examples)
- Policies: POST /api/policies (ADMIN), POST /api/policies/{id}/premium (ADMIN), GET /api/policies, GET /api/policies/type/{type}
- Customer Policies: POST /api/customer-policies/purchase, POST /api/customer-policies/{id}/cancel, GET /api/customer-policies/customer/{customerId}
- Claims: POST /api/claims, POST /api/claims/{id}/status (AGENT/ADMIN), GET /api/claims/ref/{reference}, GET /api/claims/customer/{customerId}
- Payments: POST /api/payments/premium, POST /api/payments/payout (AGENT/ADMIN), GET /api/payments/customer/{customerId}
- Reports: GET /api/reports/claims (ADMIN), GET /api/reports/premiums (ADMIN)

Quick test
1) Register an admin:
   curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d '{"fullName":"Alice","email":"alice@example.com","password":"secret","roles":["ADMIN"]}'
2) Call a protected endpoint:
   curl http://localhost:8080/api/admin/roles -H "Authorization: Bearer <token>"
3) Swagger UI:
   http://localhost:8080/swagger-ui.html