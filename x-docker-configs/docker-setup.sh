#!/bin/bash



# Lancer les services avec Docker Compose
echo "Lancement des services Docker avec docker-compose..."

# Lancer tous les services dans les différents fichiers docker-compose
docker-compose -f docker-compose-kafka.yml up -d
sleep 10
docker-compose -f docker-compose-elk_setup.yml -f docker-compose-elk.yml up -d
docker-compose -f docker-compose-app.yml up -d

# Vérification des conteneurs
echo "Vérification des conteneurs en cours d'exécution..."
docker ps

echo "Tous les conteneurs sont maintenant lancés et opérationnels !"