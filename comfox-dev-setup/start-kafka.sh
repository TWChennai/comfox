#!/bin/bash

if [[ -z "$KAFKA_BROKER_ID" ]]; then
	export KAFKA_BROKER_ID=$KAFKA_ADVERTISED_PORT
fi

if [[ -z "$KAFKA_LOG_DIRS" ]]; then
	export KAFKA_LOG_DIRS="/kafka/kafka-logs-1"
fi

# editing server.properties in accordance with the docker run parameters
for VAR in `env`
  do
     if [[ $VAR =~ ^KAFKA_ && ! $VAR =~ ^KAFKA_HOME ]]; then
	kafka_name=`echo "$VAR" | sed -r "s/KAFKA_(.*)=.*/\1/g" | tr '[:upper:]' '[:lower:]' | tr _ .`
	env_var=`echo "$VAR" | sed -r "s/(.*)=.*/\1/g"`
	if egrep -q "(^|^#)$kafka_name=" $KAFKA_HOME/config/server.properties; then
		sed -r -i "s@(^|^#)($kafka_name)=(.*)@\2=${!env_var}@g" $KAFKA_HOME/config/server.properties
	else
	   echo "$kafka_name=${!env_var}" >> $KAFKA_HOME/config/server.properties
	fi
     fi
 done

 $KAFKA_HOME/bin/zookeeper-server-start.sh -daemon  $KAFKA_HOME/config/zookeeper.properties  
 $KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties 
