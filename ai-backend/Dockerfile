# ===== Stage 1: Build with Maven (Java 22) =====
FROM maven:3.9.9-eclipse-temurin-22 AS builder

# Set the working directory to the project root
WORKDIR /app

# Copy only Maven project descriptor files (for dependency caching)
COPY pom.xml .
COPY ai-backend-application/pom.xml ai-backend-application/
COPY ai-backend-domain/pom.xml ai-backend-domain/
COPY ai-backend-infrastructure/pom.xml ai-backend-infrastructure/
COPY ai-backend-api/pom.xml ai-backend-api/

# Pre-fetch dependencies
RUN mvn dependency:go-offline -B

# Copy the source code
COPY ai-backend-application/src ai-backend-application/src
COPY ai-backend-domain/src ai-backend-domain/src
COPY ai-backend-infrastructure/src ai-backend-infrastructure/src
COPY ai-backend-api/src ai-backend-api/src

# Build the application (skip tests for faster CI/CD)
RUN mvn clean package -DskipTests

# ===== Stage 2: Run with Java 22 JRE =====
FROM eclipse-temurin:22-jre

# Set working directory
WORKDIR /app

# Copy the built jar from builder
COPY --from=builder /app/ai-backend-api/target/ai-backend-api-*.jar app.jar

# Run as non-root for security
RUN useradd -m appuser
USER appuser

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
