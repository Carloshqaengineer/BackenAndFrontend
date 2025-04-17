# Usa la imagen oficial de Java 17
FROM eclipse-temurin:17-jdk-jammy

# Copia el código fuente
COPY . /app
WORKDIR /app

# Build con Maven (o Gradle)
RUN ./mvnw clean package

# Puerto expuesto (el mismo que usa Spring Boot, usualmente 8080)
EXPOSE 8080

# Comando para iniciar la aplicación
CMD ["java", "-jar", "target/backandfront-0.0.1-SNAPSHOT.jar"]