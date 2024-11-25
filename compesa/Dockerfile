# Use a Maven image with the desired OpenJDK version
FROM maven:3.8.8-eclipse-temurin-17

# Set the working directory inside the container
WORKDIR /app

# Copy only the subdirectory contents into the container
COPY compesa /app

# Build the application
RUN mvn clean package -DskipTests

# Run the application
CMD ["java", "-jar", "target/compesa-0.0.1-SNAPSHOT.jar"]
