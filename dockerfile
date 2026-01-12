FROM eclipse-temurin:17-jdk-jammy
COPY target/* .
EXPOSE 8080
CMD ["java", "-jar", "flightreservationApplication-0.0.1-SNAPSHOT.jar "]
