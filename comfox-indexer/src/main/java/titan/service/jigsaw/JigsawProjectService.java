package titan.service.jigsaw;


import model.jigsaw.Project;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import titan.service.GraphService;
import titan.service.MessageService;

import java.util.Optional;

public class JigsawProjectService extends GraphService {

    public Vertex createProjectNode(Project project) {
        Vertex vertex = titanGraph.addVertex(T.label, "project", "projectId", project.getId(),
                "name", project.getName(), "type", project.getType());
        titanGraph.tx().commit();
        return vertex;
    }

    public Optional<Vertex> getProjectNodeById(Project project) {
        GraphTraversal<Vertex, Vertex> projectNodeAfterTraversal = titanGraph.traversal().V().has("project",
                "projectId", project.getId());
        return Optional.ofNullable(projectNodeAfterTraversal.hasNext() ? projectNodeAfterTraversal.next() : null);
    }
}
