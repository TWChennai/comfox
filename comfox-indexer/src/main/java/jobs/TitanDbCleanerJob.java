package jobs;

import com.thinkaurelius.titan.core.TitanGraph;
import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import titan.service.GraphService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TitanDbCleanerJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        GraphService graphService = new GraphService();
        TitanGraph titanGraph = graphService.titanGraph;

        deleteOldMessages(titanGraph, "slack");
        deleteOldMessages(titanGraph, "twitter");
        deleteOldRelatedMessages(titanGraph);


        titanGraph.tx().commit();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String time = sdf.format(new Date());
        System.out.println("DB cleaned  up at: " + time);
    }

    private void deleteOldRelatedMessages(TitanGraph titanGraph) {
        GraphTraversal<Vertex, Vertex> traversal = titanGraph.traversal().V().hasLabel("relatedMessage");
        Long totalMessageCount = traversal.count().next();
        if (totalMessageCount > 40) {
            titanGraph.traversal().V().hasLabel("relatedMessage").order().by("timestamp", Order.incr).limit(totalMessageCount - 40).drop().iterate();
        }
    }

    private void deleteOldMessages(TitanGraph titanGraph, String channel) {
        GraphTraversal<Vertex, Vertex> traversal = titanGraph.traversal().V().hasLabel("message").has("channel", channel);
        Long totalMessageCount = traversal.count().next();
        if (totalMessageCount > 25) {
            titanGraph.traversal().V().hasLabel("message").order().by("timestamp", Order.incr).limit(totalMessageCount - 25).drop().iterate();
        }
    }
}
