FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /build

RUN apk add --no-cache maven
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# ==== Runtime =====
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

COPY --from=build /build/target/*-SNAPSHOT.jar app-0.0.2.jar

ENTRYPOINT ["java", "-jar", "app-0.0.2.jar"]
