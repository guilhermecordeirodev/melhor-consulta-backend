# Etapa de build
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# Etapa de execução
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

# Ajustando a JVM para melhor performance
ENV JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseContainerSupport -XX:InitialRAMPercentage=50 -XX:MaxRAMPercentage=85 -XX:+AlwaysPreTouch"

EXPOSE 8080
CMD ["java", "-jar", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseContainerSupport", "-XX:InitialRAMPercentage=50", "-XX:MaxRAMPercentage=85", "-XX:+AlwaysPreTouch", "app.jar"]
