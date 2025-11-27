# ===== Build stage =====
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# ===== Runtime stage =====
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

ENTRYPOINT ["sh", "-c", "java $JAVA_TOOL_OPTIONS -XX:HeapDumpPath=/heap/input/heap_${HOSTNAME}_$(uuidgen).hprof -jar app.jar"]


