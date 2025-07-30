# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /code

# Copy the project JAR file into the container at /code
COPY target/*.jar /code/qr_menu.jar

# Expose the port the app runs on
EXPOSE 8083

# Run the JAR file
ENTRYPOINT ["java", "-jar", "qr_menu.jar"]