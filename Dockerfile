# Imagem base do Java
FROM eclipse-temurin:21-jdk

# Define o diretório de trabalho
WORKDIR /app

# Copia o jar gerado
COPY target/*.jar app.jar

# Expõe a porta do Spring Boot
EXPOSE 8080

# Comando para rodar o jar
ENTRYPOINT ["java", "-jar", "app.jar"]

