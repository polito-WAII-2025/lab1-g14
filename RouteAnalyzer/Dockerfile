# BUILDER
FROM openjdk:17-jdk-slim AS builder

# Setup working dir
WORKDIR /app

# Setup gradle
COPY gradlew gradlew.bat ./
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts ./
RUN chmod +x gradlew

RUN ./gradlew dependencies --no-daemon
COPY src ./src

RUN ./gradlew shadowJar --no-daemon

# RUNNER
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

CMD ["exec", "@"]
