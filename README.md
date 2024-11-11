Voici la documentation mise à jour en français :

# **Nom de l'Application**

---

## **Table des matières**

- [Introduction](#introduction)
- [Prérequis](#prérequis)
- [Architecture](#architecture)
- [Installation](#installation)
  - [Dépendances](#dépendances)
  - [Docker Compose](#docker-compose)
- [Configuration](#configuration)
  - [Configuration Spring Cloud Stream & Kafka](#spring-cloud-stream--kafka-configuration)
  - [Configuration Logstash](#logstash-configuration)
  - [Paramètres de l'application](#application-properties)
- [Utilisation](#utilisation)
  - [Kafka Producteur](#kafka-producer)
  - [Kafka Consommateur (optionnel)](#kafka-consumer-optionnel)
  - [Logs](#logs)
- [Problèmes courants](#problèmes-courants)
- [Contribution](#contribution)
- [Licence](#licence)

---

## **Introduction**

Cette application utilise Logstash pour :
- collecter les données provenant de Kafka ;
- collecter les logs provenant d'une application Spring.

Les configurations de Logstash envoient les données à Elasticsearch et sont visualisables via Kibana.

---

## **Prérequis**

Avant de commencer, assurez-vous d'avoir installé et configuré les éléments suivants :

- **Java 17**
- **docker**
- **Docker-compose**

---

## **Architecture**


L'ensemble des services (Kafka, Logstash, Elasticsearch et l'application Spring) est géré dans un **réseau Docker** pour simplifier l'intégration et le déploiement.

[Logstash Ingestion](logstash-ingestion.png)


---

## **Installation**

### 1. Créer le(s) réseau(x) Docker nécessaire(s)

Utilisez cette commande pour créer le(s) réseau(x) Docker :

```bash
chmod +x docker-configs/docker-create-network.sh
./docker-configs/docker-create-network.sh
```

### 2. Créer la stack Kafka

```bash
docker-compose -f docker-configs/docker-compose-kafka.yml up -d
```

Création du topic dans le conteneur Kafka :

```bash
chmod +x docker-configs/kafka-create-topic.sh
./docker-configs/kafka-create-topic.sh
```

### 3. Créer la stack ELK

```bash
docker-compose -f docker-compose-elk_setup.yml -f docker-compose-elk.yml up -d
```

### 4. Créer le conteneur de l'application

```bash
./mvnw clean install
docker-compose -f docker-compose-app.yml up -d
```

## **Configuration**

### 1. Configuration de Logstash

Dans le fichier `logstash.conf`, configurez les entrées pour recevoir les logs de Kafka et de l'application, puis envoyez les données vers Elasticsearch.

#### a. Logstash - Kafka

Définit un type spécifique pour les données Kafka :

```plaintext
input {
  kafka {
    bootstrap_servers => "kafka:9092"
    topics => ["pm-events"]
    codec => json
    group_id => "pocmaster_logstash"
    type => "kafka_event"
  }
}
```

#### b. Logstash - Logs d'application

Définit un type spécifique pour les logs de l'application :

```plaintext
input {
  tcp {
    port => 5000
    codec => json
    type => "application_log"
  }
}
```

#### c. Filtrage Logstash

Consultez la section `filter` dans `logstash.conf` pour voir la logique de transformation appliquée en fonction du type d'entrée.

#### d. Sortie vers Elasticsearch

Logstash est configuré pour envoyer les données vers Elasticsearch :

```plaintext
output {
  elasticsearch {
    index => "logstash-%{+YYYY.MM.dd}"
    hosts => "${ELASTIC_HOSTS}"
    user => "${ELASTIC_USER}"
    password => "${ELASTIC_PASSWORD}"
    cacert => "certs/ca/ca.crt"
  }
}
```

### 2. Configuration de l'Application Spring

L'application utilise Spring Cloud Stream pour gérer les événements publiés vers Kafka.

#### 2.a Configuration Maven

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream-binder-kafka</artifactId>
  </dependency>
</dependencies>
```

#### 2.b Configuration Kafka

Dans le fichier `application.yml`, le profil `kafka-scs-local` est utilisé pour configurer le binder Kafka :

```yaml
spring:
  cloud:
    stream:
      binders:
        kafka-local:
          type: kafka
          environment:
            spring.cloud.stream.kafka.binder:
              brokers: kafka:9092
              configuration:
                security.protocol: PLAINTEXT
```

#### 2.c Producteur d'événements

L'application produit des événements à intervalles définis par `@Scheduled` :

```java
@Scheduled(fixedDelayString = "${task.schedule.fixedDelay}")
public void produceEvent() {
    //...
  streamBridge.send(TOPIC, pmEvent);
  //...
}
```

```yaml
task.schedule.fixedDelay=5000
```

#### 2.d Paramètres de l'Application

Dans `application.properties`, configurez des paramètres comme les délais ou les informations de logs :

```yaml
app:
  task:
    schedule:
      fixed-delay: 5000 # toutes les 5 secondes
```

#### 2.f Producteur de Logs

Les logs générés par l'application sont envoyés vers Logstash via `logback-spring.xml` :

```xml
<appender name="LOGSTASH" class="ch.qos.logback.core.net.SyslogAppender">
    <syslogHost>logstash01:5000</syslogHost>
</appender>

<root level="info">
    <appender-ref ref="LOGSTASH"/>
</root>
```

---

## **Contribution**

Nous encourageons les contributions à ce projet. Si vous souhaitez proposer une amélioration ou corriger un bug, veuillez ouvrir une **Pull Request**. Assurez-vous de tester vos modifications avant de les soumettre.