FROM maven:3.8.7-openjdk-21
COPY . /app
WORKDIR /app
RUN mvn clean install
CMD ["java", "-jar", "target/compesa-0.0.1-SNAPSHOT.jar"]
