FROM eclipse-temurin:17-jdk-jammy
COPY target/* .
EXPOSE 8080
CMD ["java", "-jar", "flight-reservation-app-1.0-SNAPSHOT.jar"]
