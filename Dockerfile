# Etapa 1: Construção da aplicação (build)
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app

# Copiar apenas arquivos necessários para evitar invalidar cache
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts gradlew ./
RUN chmod +x gradlew

# Baixar as dependências antes de copiar o código-fonte (cache eficiente)
RUN ./gradlew dependencies --no-daemon

# Agora copiamos o código-fonte
COPY src src
RUN ./gradlew build --no-daemon

# Etapa 2: Execução da aplicação (imagem final enxuta)
FROM eclipse-temurin:17-jre AS runtime
WORKDIR /app

# Criar um usuário não-root para maior segurança
RUN groupadd -g 1001 appgroup && useradd -r -u 1001 -g appgroup appuser
USER appuser

# Copiar apenas o JAR necessário
COPY --from=builder /app/build/libs/*.jar app.jar

# Ajustando a JVM para evitar erros de memória e threads
ENV JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseContainerSupport -XX:InitialRAMPercentage=30 -XX:MaxRAMPercentage=70 -XX:ParallelGCThreads=2 -XX:ConcGCThreads=2 -XX:CICompilerCount=2 -Xss512k"

EXPOSE 8080

CMD ["java", "-jar", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseContainerSupport", "-XX:InitialRAMPercentage=30", "-XX:MaxRAMPercentage=70", "-XX:ParallelGCThreads=2", "-XX:ConcGCThreads=2", "-XX:CICompilerCount=2", "-Xss512k", "app.jar"]
