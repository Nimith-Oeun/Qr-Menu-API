#
# Build stage
#
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app
COPY . /app/
RUN mvn clean package -DskipTests

#
# package stage
#
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar /app/qr_menu.jar
EXPOSE 8083
# Run the JAR file
ENTRYPOINT ["java", "-jar", "qr_menu.jar"]