# Etapa 1: Compilación
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

# en ms-account, Copiar solo el JAR resultante de la etapa anterior 
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "app.jar"]