FROM openjdk:11-jdk
ARG JAR_FILE=builds/libs/*.jar
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]
