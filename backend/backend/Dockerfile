# Stage 1: Build
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk
COPY --from=build /target/*.jar app.jar
# Render uses a dynamic port; this tells Spring to listen to it
ENTRYPOINT ["java","-Dserver.port=${PORT:8080}","-jar","app.jar"]