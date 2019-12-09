FROM ubuntu:trusty
RUN apt-get update -y  &&  apt-get install -y openjdk-7-jre
RUN apt-get -f install &&  apt-get install -y wget
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*
RUN wget -q http://a.mbbsindia.com/kafka/0.9.0.1/kafka_2.10-0.9.0.1.tgz  -O /tmp/kafka_2.10-0.9.0.1.tgz
RUN tar xfz /tmp/kafka_2.10-0.9.0.1.tgz -C /opt
ENV KAFKA_HOME /opt/kafka_2.10-0.9.0.1
ADD start-kafka.sh /usr/local/bin/start-kafka.sh
CMD start-kafka.sh
