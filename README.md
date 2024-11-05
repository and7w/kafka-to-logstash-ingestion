# kafka-to-logstash-pipeline


docker swarm init
docker stack deploy --compose-file ./x-docker-elk-configs/docker-compose.yml elk
#docker stack deploy --compose-file ./x-docker-kafka-configs/docker-compose.yml kafka
docker stack deploy --compose-file docker-compose.yml app


docker stack services elk
docker stack services kafka
docker stack services app


FROM openjdk:17-alpine as build

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src


RUN chmod +x mvnw
RUN ./mvnw clean install -X -e
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../kafka-to-logstash-ingestion-1.0.3.jar)

FROM openjdk:17-alpine


VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app


ENTRYPOINT ["java","-cp","app:app/lib/*","com.pocmaster.demo.Kafka2LogstashApp"]





