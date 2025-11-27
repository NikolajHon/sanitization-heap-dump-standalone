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

# enable edge repos to install curl
RUN echo "https://dl-cdn.alpinelinux.org/alpine/edge/main" >> /etc/apk/repositories && \
    echo "https://dl-cdn.alpinelinux.org/alpine/edge/community" >> /etc/apk/repositories && \
    apk update && apk add --no-cache curl

# download heap-dump-tool
RUN mkdir -p /opt/heap-dump-tool && \
    curl -L -o /opt/heap-dump-tool/heap-dump-tool.jar \
      https://repo1.maven.org/maven2/com/paypal/heap-dump-tool/1.3.3/heap-dump-tool-1.3.3-all.jar

COPY --from=build /build/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
