FROM maven:3.8.5-openjdk-21-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=build /app/target/compesa-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]