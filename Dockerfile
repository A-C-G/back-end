FROM adoptopenjdk/openjdk11

COPY ./build/libs/ACG-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]