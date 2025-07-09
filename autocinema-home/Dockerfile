# Usa una imagen base de Java
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Variable para el perfil
ENV SPRING_PROFILES_ACTIVE=docker

# Permite usar cualquier nombre de JAR generado
ARG JAR_FILE=target/*.jar

# Copia el JAR al contenedor
COPY ${JAR_FILE} app.jar

# Expone el puerto de Spring Boot (reemplaza según el puerto de cada microservicio si necesitas)
EXPOSE 8001

# Comando para ejecutar el JAR
CMD ["java", "-jar", "app.jar"]

