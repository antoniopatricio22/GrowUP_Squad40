FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY compesa /app
RUN mvn clean install -U

FROM openjdk:21
WORKDIR /app
COPY --from=build /app/database /app/database
COPY --from=build /app/target/compesa-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
