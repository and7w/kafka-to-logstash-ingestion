# Variables
CONTAINER_NAME="x-docker-configs-kafka-1"
TOPIC_NAME="pm_events"
BOOTSTRAP_SERVER="kafka:9092"
#Connectez-vous à votre conteneur Kafka et trouvez le chemin de `kafka-topics.sh` avec cette commande `find / -name kafka-topics.sh`
KAFKA_TOPICS_SCRIPT="/opt/kafka_2.12-2.3.0/bin/kafka-topics.sh"

# Exécuter la commande
docker exec $CONTAINER_NAME $KAFKA_TOPICS_SCRIPT \
  --create \
  --topic $TOPIC_NAME \
  --bootstrap-server $BOOTSTRAP_SERVER \
  --partitions 1 \
  --replication-factor 1


TOPICS=$(docker exec "$KAFKA_CONTAINER" "$KAFKA_SCRIPT_PATH" --list --bootstrap-server "$BOOTSTRAP_SERVER")

# Vérifier si le topic existe
if echo "$TOPICS" | grep -q "$TOPIC_NAME"; then
  echo "Le topic '$TOPIC_NAME' a ete correctement cree"
else
  echo "Le topic '$TOPIC_NAME' n'existe pas dans Kafka."
fi