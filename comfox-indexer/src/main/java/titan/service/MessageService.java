package titan.service;

import config.ConfigManager;
import model.ComfoxMessage;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class MessageService<T extends ComfoxMessage> extends GraphService {

    public void interpretMessage(T message) throws URISyntaxException, IOException {
        String specialCharacter = "[#@]";
        if (!statusAlreadyPresent(message)) {
            Vertex messageNode = createMessageNode(message);
            Matcher matcher = Pattern.compile(specialCharacter + "\\s*(\\w+)").matcher(message.getMessage());
            addListOfRelatedToEdges(matcher, messageNode);
        }
    }

    private boolean statusAlreadyPresent(T message) {
        return message.getIsAStatus().equals("true") && titanGraph.traversal().V()
                .has("username", message.getUsername())
                .has("content", message.getMessage())
                .hasNext();
    }

    public void addListOfRelatedToEdges(Matcher matcher, Vertex message) {
        while (matcher.find()) {
            String hashTag = matcher.group(1);
            if (!hashTag.toLowerCase().equals("comfox")) {
                String skillName = hashTag.replaceAll("(?<=.{2,})(?=[A-Z].*[a-z]{1,})", " ");
                Optional<Vertex> vertexByName = findVertexByName(skillName);
                Vertex adjacentNode = vertexByName.isPresent() ? vertexByName.get() : createSkillNode(skillName.toLowerCase());
                if (!isRelated(message, adjacentNode)) {
                    adjacentNode.addEdge("related_to", message);
                }
                titanGraph.tx().commit();
            }
        }
    }

    protected Optional<Vertex> findVertexByName(String vertexIdentifier) {
        GraphTraversal<Vertex, Vertex> vertex = titanGraph.traversal().V().has("name", vertexIdentifier);
        GraphTraversal<Vertex, Vertex> vertexLower = titanGraph.traversal().V().has("name", vertexIdentifier.toLowerCase());
        if (!vertex.hasNext()) {
            vertex = vertexLower;
        }

        return Optional.ofNullable(vertex.hasNext() ? vertex.next() : null);
    }

    protected abstract Vertex createMessageNode(T message);

    // assuming the non-existing node to be a skill need to modify it
    protected Vertex createSkillNode(String vertexIdentifier) {
        return titanGraph.addVertex(org.apache.tinkerpop.gremlin.structure.T.label, "skill", "name", vertexIdentifier);
    }
}
