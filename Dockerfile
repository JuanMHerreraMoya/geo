# Etapa 1: Construcción de la aplicación
FROM jelastic/maven:3.9.5-openjdk-21 AS build

WORKDIR /app

# Copia el archivo pom.xml y descarga las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia el código fuente
COPY src ./src

# Construye la aplicación
RUN mvn clean package -DskipTests

# Etapa 2: Construcción de la imagen de producción
FROM openjdk:21-jdk

WORKDIR /app

# Copia el JAR construido desde la etapa anterior
COPY --from=build /app/target/geo-0.0.1-SNAPSHOT.jar /app/geo-0.0.1-SNAPSHOT.jar

# Ejecuta la aplicación
CMD ["java", "-jar", "/app/geo-0.0.1-SNAPSHOT.jar"]