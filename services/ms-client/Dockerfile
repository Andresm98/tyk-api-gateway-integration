# Etapa 1: Compilación ms-client
FROM maven:3.8.5-openjdk-11-slim AS build
WORKDIR /app

# Copiar el pom y descargar dependencias (optimiza caché)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests -e -X

# Etapa 2: Ejecución (Imagen ligera)
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiar solo el JAR resultante de la etapa anterior ms-client
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "app.jar"]