# Estágio de construção (Build)
FROM amazoncorretto:17 AS builder

WORKDIR /app

# Copiar apenas arquivos essenciais primeiro para maximizar cache útil
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Baixar dependências primeiro para evitar re-build desnecessário
RUN chmod +x gradlew && ./gradlew dependencies

# Agora copiamos o código-fonte e rodamos a build
COPY src src
RUN ./gradlew clean build

# Estágio de runtime
FROM amazoncorretto:17

WORKDIR /app

# Copiar o JAR construído
COPY --from=builder /app/build/libs/melhorconsulta-api-1.0.28.jar app.jar

# Expor a porta da aplicação
EXPOSE 8080

# Comando de execução
ENTRYPOINT ["java", "--add-opens", "java.base/java.time=ALL-UNNAMED", "-jar", "app.jar"]