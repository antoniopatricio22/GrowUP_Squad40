# Build Stage
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app

# Copia os arquivos necessários
COPY compesa /app

# Executa o build do Maven
RUN mvn clean install -U

# Runtime Stage
FROM openjdk:21
WORKDIR /app

# Copia o JAR e o banco de dados para o contêiner
COPY --from=build /app/target/compesa-0.0.1-SNAPSHOT.jar /app/app.jar
COPY compesa/.env /app/.env

# O banco de dados SQLite será montado como volume para persistência
VOLUME /app/database
EXPOSE 8080

# Configura o uso do .env
ENV DOTENV_CONFIG_PATH=/app/.env

# Executa a aplicação
CMD ["java", "-Dspring.config.location=classpath:/application.properties", "-jar", "app.jar"]
