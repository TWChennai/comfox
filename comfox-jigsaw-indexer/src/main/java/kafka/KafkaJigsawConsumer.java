package kafka;

import kafka.messageTags.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import service.AssignmentService;
import service.PersonService;
import service.ProjectService;
import service.SkillService;

import java.util.*;

public class KafkaJigsawConsumer {
    private KafkaConsumer<String, String> kafkaConsumer;
    private MessageTagParser messageTagParser;

    public KafkaJigsawConsumer(List<String> topics){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "jigsaw-consumer");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        kafkaConsumer = new KafkaConsumer<String, String>(props);

        PersonService personService = new PersonService();
        ProjectService projectService = new ProjectService();
        SkillService skillService = new SkillService();
        AssignmentService assignmentService = new AssignmentService(personService, projectService);

        Map<String, MessageTag> tagMap = new HashMap<>();

        tagMap.put("assignments", new AssignmentsTag(assignmentService));
        tagMap.put("persons", new PeopleTag(personService));
        tagMap.put("skills", new SkillsTag(personService, skillService));

        messageTagParser = new MessageTagParser(tagMap);

        kafkaConsumer.subscribe(topics);
    }

    public void consumeMessages(){
        try{
                while(true) {
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(200);
                    for (ConsumerRecord<String, String> record : records) {
                        String tag = record.key();
                        String jsonData = record.value();

                        MessageTag messageTag = messageTagParser.parse(tag);

                        messageTag.execute(tag, jsonData);
                    }
                }
        }
        finally {
            kafkaConsumer.close();
        }
    }

    public static void main(String[] args) {
        KafkaJigsawConsumer kafkaJigsawConsumer = new KafkaJigsawConsumer(Arrays.asList("jigsaw-test"));
        kafkaJigsawConsumer.consumeMessages();
    }
}
