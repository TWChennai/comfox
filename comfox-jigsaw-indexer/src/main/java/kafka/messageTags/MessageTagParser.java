package kafka.messageTags;

import service.AssignmentService;
import service.PersonService;
import service.ProjectService;

import java.util.Map;

public class MessageTagParser {
    Map<String, MessageTag> tagMap;

    public MessageTagParser(Map<String, MessageTag> tagMap) {
        this.tagMap = tagMap;
    }

    public MessageTag parse(String messageTag){
        if(messageTag.contains("skills")){
            messageTag = "skills";
        }
        return tagMap.get(messageTag);
    }
}
