package com.comfox.api.services;


import com.comfox.api.models.ComfoxMessage;
import com.thinkaurelius.titan.core.TitanGraph;
import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public class MessageService {
    private TitanGraph titanGraph;
    private static final long LIMIT_ON_MESSAGES = 25L;
    private static final long LIMIT_ON_NUMBER_OF_MESSAGES_DISPLAYED = 5L;
    private static final long LIMIT_ON_NUMBER_OF_STATUSES_DISPLAYED = 5L;
    private static final int LIMIT_ON_RELATED_MESSAGES = 2;
    private static final long LIMIT_ON_STATUSES = 25L;
    private static final double probability = 0.28;
    List<ComfoxMessage> emptyList = Collections.emptyList();


    public MessageService(TitanGraph titanGraph) {
        this.titanGraph = titanGraph;
    }

    public List<ComfoxMessage> getRecentTwitterMessages() {
        GraphTraversalSource traversal = titanGraph.traversal();
        GraphTraversal<Vertex, Vertex> graphTraversal = traversal.V().has("message", "channel", "twitter").has("content").order().by("timestamp", Order.decr).limit(LIMIT_ON_MESSAGES).coin(probability).limit(LIMIT_ON_NUMBER_OF_MESSAGES_DISPLAYED);
        return getRecentMessages(traversal, graphTraversal);
    }

    public List<ComfoxMessage> getRecentSlackMessages() {
        GraphTraversalSource traversal = titanGraph.traversal();
        GraphTraversal<Vertex, Vertex> graphTraversal = traversal.V().has("message", "channel", "slack").has("content").order().by("timestamp", Order.decr).limit(LIMIT_ON_MESSAGES).coin(probability).limit(LIMIT_ON_NUMBER_OF_MESSAGES_DISPLAYED);
        return getRecentMessages(traversal, graphTraversal);
    }


    public List<ComfoxMessage> getRecentMessages(GraphTraversalSource traversal, GraphTraversal<Vertex, Vertex> graphTraversal) {
        // handle when any one of the attributes is not present
        List<ComfoxMessage> messages = new ArrayList<>();

        while (graphTraversal.hasNext()) {
            Vertex messageVertex = graphTraversal.next();
            List<ComfoxMessage> relatedMessages = new ArrayList<>();
            relatedMessages.addAll(generateProjectRelatedMessages(traversal, messageVertex));
            relatedMessages.addAll(getSkillRelatedMessages(traversal, messageVertex));
            ComfoxMessage comfoxMessage = new ComfoxMessage(
                    messageVertex.property("content").value().toString(),
                    messageVertex.property("username").value().toString(),
                    messageVertex.property("imageurl").value().toString(),
                    relatedMessages);
            if (!messages.contains(comfoxMessage)) {
                messages.add(comfoxMessage);
            }
        }
        return messages;
    }

    public List<ComfoxMessage> getRelatedMessages(Vertex messageVertex) {
        GraphTraversalSource traversal = titanGraph.traversal();
        List<ComfoxMessage> relatedMessages = new ArrayList<>();
        relatedMessages.addAll(generateProjectRelatedMessages(traversal, messageVertex));
        relatedMessages.addAll(getSkillRelatedMessages(traversal, messageVertex));
        return relatedMessages;
    }

    private List<ComfoxMessage> getSkillRelatedMessages(GraphTraversalSource traversal, Vertex messageVertex) {
        List<ComfoxMessage> comfoxMessages = new ArrayList<>();
        try {
            // loop through the skills set of a message and the related messages of each skill
            GraphTraversal<Vertex, Vertex> skillTraversal = traversal.V(messageVertex).both().hasLabel("skill");
            while (skillTraversal.hasNext()) {
                Vertex skillVertex = skillTraversal.next();
                GraphTraversal<Vertex, Vertex> related_to_traversal = traversal.V(skillVertex).outE("related_to").inV().hasLabel("relatedMessage").order().by("timestamp", Order.decr);
                while (related_to_traversal.hasNext()) {
                    Vertex relatedMessageVertex = related_to_traversal.next();
                    ComfoxMessage comfoxMessage = new ComfoxMessage(relatedMessageVertex.value("content").toString(), relatedMessageVertex.value("username"), relatedMessageVertex.value("imageurl"), null);
                    if (!comfoxMessages.contains(comfoxMessage) && (!comfoxMessage.getContent().contains(messageVertex.property("content").value().toString()))) {
                        comfoxMessages.add(comfoxMessage);
                    }
                }
            }
            return comfoxMessages.subList(0, LIMIT_ON_RELATED_MESSAGES);
        } catch (Exception e) {
            return asList();
        }
    }

    private List<ComfoxMessage> generateProjectRelatedMessages(GraphTraversalSource traversal, Vertex messageVertex) {
        try {
            // assumption there will be only one project mentioned in a message
            Vertex project = traversal.V(messageVertex).both().hasLabel("project").next();
            Long peopleCount = traversal.V(project).inE("assigned_to").count().next();
            return asList(new ComfoxMessage(
                    String.format("%s has %s people", project.property("name").value().toString(), peopleCount),
                    "comfox", null, null));
        } catch (Exception e) {
            return asList();
        }
    }

    public List<ComfoxMessage> getRecentStatuses() {

        GraphTraversalSource traversal = titanGraph.traversal();
        GraphTraversal<Vertex, Vertex> graphTraversal = traversal.V().has("message", "isAStatus", "true").has("content").order().by("timestamp", Order.decr).limit(LIMIT_ON_STATUSES).coin(probability).limit(LIMIT_ON_NUMBER_OF_STATUSES_DISPLAYED);
        return getStatues(graphTraversal);
    }

    private List<ComfoxMessage> getStatues(GraphTraversal<Vertex, Vertex> graphTraversal) {
        List<ComfoxMessage> comfoxMessages = new ArrayList<>();
        while (graphTraversal.hasNext()) {
            Vertex relatedMessageVertex = graphTraversal.next();
            comfoxMessages.add(new ComfoxMessage(relatedMessageVertex.value("content").toString(), relatedMessageVertex.value("username"), relatedMessageVertex.value("imageurl"), emptyList));
        }
        return comfoxMessages;
    }

    public Vertex getSlackMessageVertex(String content) {
        GraphTraversal<Vertex, Vertex> vertexGraphTraversal = titanGraph.traversal().V().has("message", "channel", "slack").has("content", content);
        return vertexGraphTraversal.hasNext() ? vertexGraphTraversal.next() : null;
    }
}
