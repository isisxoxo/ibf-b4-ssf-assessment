## FIRST CONTAINER

# Build container
FROM openjdk:21-jdk-bullseye AS builder

# Create a directory for our application
WORKDIR /app

COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

# Compile into: /target/ibfb4ssfassessment-0.0.1-SNAPSHOT.jar
RUN /app/mvnw package -Dmaven.test.skip=true




## SECOND CONTAINER

# Run container
FROM openjdk:21-jdk-bullseye

WORKDIR /app_run

# Copying file from builder instead of locally
COPY --from=builder /app/target/ibfb4ssfassessment-0.0.1-SNAPSHOT.jar .
COPY --from=builder /app/movies.json /app_run/movies.json

# Run
ENV API_KEY=abc123
ENV PORT=8080

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar ibfb4ssfassessment-0.0.1-SNAPSHOT.jar