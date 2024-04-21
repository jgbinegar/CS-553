FROM eclipse-temurin:21-jre
COPY out/artifacts/WebApp/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
