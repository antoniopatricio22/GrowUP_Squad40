FROM maven:3.9.8-eclipse-temurin-21 AS build
#COPY compesa/src /app/src
#COPY compesa/pom.xml /app
COPY compesa /app

WORKDIR /app
RUN mvn clean install -U

FROM openjdk:21
COPY --from=build /app/target/compesa-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
