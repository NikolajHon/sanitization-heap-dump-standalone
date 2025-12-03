FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# ==== Runtime =====
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /build/target/*-SNAPSHOT.jar app-0.0.2.jar

ENTRYPOINT ["java", "-jar", "app-0.0.2.jar"]
