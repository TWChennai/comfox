package kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class Producer
{
    private final KafkaProducer<String, String> producer;
    private final String topic;


    public Producer(String topic, String host, String port)
    {
        Properties props = new Properties();
        props.put("bootstrap.servers", host+":"+port);
        props.put("client.id", "ComfoxProducer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<String, String>(props);
        this.topic = topic;
    }

    public Producer(String topic)
    {
        this(topic, "localhost", "9092");
    }

    public void send(String messageId, String message) {
        long startTime = System.currentTimeMillis();

        producer.send(new ProducerRecord<String, String>(topic,
                messageId,
                message), new CallBackImpl(startTime, messageId, message));

        producer.flush();
    }
}

