# ===== Build stage =====
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build

# copy Maven descriptor and download dependencies
COPY pom.xml .
RUN mvn -B dependency:go-offline

# copy source and build the Spring Boot jar
COPY src ./src
RUN mvn -B clean package -DskipTests

# ===== Runtime stage =====
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# copy fat jar from build stage
COPY --from=build /build/target/*.jar app.jar

# JVM options (HeapDumpOnOutOfMemoryError etc.) are injected via
# JAVA_TOOL_OPTIONS from docker-compose.yml
ENTRYPOINT ["sh", "-c", "java $JAVA_TOOL_OPTIONS -XX:HeapDumpPath=/heap/input/heap_${HOSTNAME}_$(uuidgen).hprof -jar app.jar"]


