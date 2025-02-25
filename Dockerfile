# Estágio de construção (Build)
FROM eclipse-temurin:17-jdk-jammy as builder

WORKDIR /app

# Copiar arquivos do Gradle primeiro para aproveitar o cache de camadas
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Construir a aplicação
RUN chmod +x gradlew && ./gradlew clean build

# Estágio de runtime
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copiar o JAR construído
COPY --from=builder /app/build/libs/*.jar app.jar

# Expor a porta da aplicação
EXPOSE 8080

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]