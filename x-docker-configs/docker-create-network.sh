#!/bin/bash

NETWORKS=("pocmaster-network")

# Création des réseaux si nécessaire
for NETWORK in "${NETWORKS[@]}"
do
  # Vérifie si le réseau existe déjà
  if ! docker network ls | grep -q "${NETWORK}"; then
    echo "Création du réseau Docker: $NETWORK"
    docker network create "$NETWORK"
  else
    echo "Le réseau $NETWORK existe déjà"
  fi
done