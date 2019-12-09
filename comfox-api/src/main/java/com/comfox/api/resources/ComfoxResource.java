package com.comfox.api.resources;

import com.comfox.api.models.ComfoxMessage;
import com.comfox.api.services.MessageService;
import org.apache.commons.collections.ListUtils;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

@Path("/comfox")
@Produces(MediaType.APPLICATION_JSON)
public class ComfoxResource {
    private MessageService messageService;

    public ComfoxResource(MessageService messageService) {
        this.messageService = messageService;
    }

    @GET
    public List<ComfoxMessage> getCommunityRelatedMessages() {

        List<ComfoxMessage> recentTwitterMessages = messageService.getRecentTwitterMessages();
        List<ComfoxMessage> recentSlackMessages = messageService.getRecentSlackMessages();

        return ListUtils.union(recentSlackMessages, recentTwitterMessages);
    }

    @GET
    @Path("/related")
    public List<String> getRelatedMessagesForSlack(@QueryParam("content") String content) {
        Vertex slackMessageVertex = messageService.getSlackMessageVertex(content);
        List<String> relatedMessages = messageService.getRelatedMessages(slackMessageVertex).stream()
                .map(ComfoxMessage::getContent).collect(Collectors.toList());
        return slackMessageVertex == null ? null : relatedMessages;

    }
}