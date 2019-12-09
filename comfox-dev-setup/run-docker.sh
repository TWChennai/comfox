#!/bin/bash
sudo mkdir -p /Users/comfox
sudo chmod -R 777 /Users/comfox
export GUEST_IP=`docker-machine ip`
docker build -t kafka-docker .
docker run --name kafka-container --rm -ti -v /Users/comfox:/tmp/kafka-logs -e KAFKA_BROKER_ID=1 -e KAFKA_ADVERTISED_HOST_NAME=`echo $GUEST_IP` --publish 2181:2181 --publish 9092:9092 kafka-docker
