
FROM maven:3.9.5-openjdk-21-slim AS builder

WORKDIR /app

COPY . .

RUN mvn clean verify

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/company-crud-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
