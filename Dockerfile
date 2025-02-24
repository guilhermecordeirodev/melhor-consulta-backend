# Etapa 1: Construção do JAR
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app

# Copia apenas os arquivos essenciais para cache eficiente
COPY gradle ./gradle
COPY gradlew .
COPY build.gradle.kts .

# Baixa as dependências antes de copiar o código-fonte (melhora cache do Docker)
RUN ./gradlew dependencies --no-daemon

# Copia o restante do código do projeto
COPY src ./src

# Compila o projeto sem rodar testes
RUN ./gradlew build -x test --no-daemon

# Etapa 2: Execução do JAR no ambiente final
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copia o JAR gerado na etapa anterior
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
CMD ["java",
     "-XX:+UseContainerSupport",
     "-XX:MaxRAMPercentage=75.0",
     "-XX:InitialRAMPercentage=25.0",
     "-XX:HeapBaseMinAddress=4g",
     "-Xss512k",
     "-XX:ParallelGCThreads=2",
     "-XX:ConcGCThreads=2",
     "-XX:MaxMetaspaceSize=256m",
     "-XX:MaxDirectMemorySize=512m",
     "-XX:+UseG1GC",
     "-jar", "app.jar"]
