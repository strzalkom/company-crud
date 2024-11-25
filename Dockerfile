# Etap budowania aplikacji
FROM maven:3.9.5-eclipse-temurin-21 AS builder

WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests

# Etap uruchamiania aplikacji
FROM eclipse-temurin:21-jre

WORKDIR /app

# Instalacja konkretnej wersji Netcat
RUN apt-get update && apt-get install -y netcat-openbsd && rm -rf /var/lib/apt/lists/*

COPY --from=builder /app/target/*.jar app.jar
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

ENTRYPOINT ["./wait-for-it.sh", "postgres", "5433", "--", "java", "-jar", "app.jar"]
