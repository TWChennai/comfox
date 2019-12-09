package titan.service.twitter;

import com.thinkaurelius.titan.core.TitanVertex;
import model.twitter.TwitterMessage;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import titan.service.MessageService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;

public class TwitterMessageService extends MessageService<TwitterMessage> {
    @Override
    protected Vertex createMessageNode(TwitterMessage message) {
        String label = message.getIsAStatus().equals("true") ? "message" : isThoughtWorksMessage(message) ? "message" : "relatedMessage";
        String modifiedMessage = isThoughtWorksMessage(message) ? message.getMessage().replace("RT @thoughtworks:", "") : message.getMessage();

        System.out.println(label);
        TitanVertex titanVertex = titanGraph.addVertex(
                T.label, label,
                "channel", "twitter",
                "username", message.getUsername(),
                "content", modifiedMessage,
                "timestamp", String.valueOf(LocalDateTime.now()),
                "imageurl", message.getImage(),
                "isAStatus", message.getIsAStatus());
        titanGraph.tx().commit();
        return titanVertex;
    }

    private boolean isThoughtWorksMessage(TwitterMessage message) {
        return message.getUsername().equals("ThoughtWorks");
    }

    @Override
    public void addListOfRelatedToEdges(Matcher matcher, Vertex message) {
        String messageUsername = message.property("username").value().toString().toLowerCase();

        if (message.property("isAStatus").value().toString().equals("false")) {
            super.addListOfRelatedToEdges(matcher, message);
        }
        if (message.property("isAStatus").value().toString().equals("false")&&!messageUsername.equals("ThoughtWorks")) {
            // relating the message node with the username as well (other than the buzzword)
            Optional<Vertex> vertexByName = findVertexByName(messageUsername);
            Vertex adjacentNode = vertexByName.isPresent() ? vertexByName.get() : createSkillNode(messageUsername);
            if (!isRelated(message, adjacentNode)) {
                adjacentNode.addEdge("related_to", message);
            }
        }
    }
}
