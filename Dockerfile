FROM eclipse-temurin:21-jre

ARG JAR_FILE=target/kafka-to-logstash-ingestion-1.0.3.jar
COPY ${JAR_FILE} /app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]

