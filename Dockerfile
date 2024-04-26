FROM eclipse-temurin:21-jre
COPY out/artifacts/WebApp/*.jar app.jar
EXPOSE 10000
PORT 0.0.0.0
ENTRYPOINT ["java", "-jar", "/app.jar"]
