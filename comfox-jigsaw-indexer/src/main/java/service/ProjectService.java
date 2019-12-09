package service;

import model.vertex.Project;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.Optional;

public class ProjectService extends GraphService {

    public Vertex createProjectNode(Project project) {
        Vertex vertex = titanGraph.addVertex(T.label, "Project", "projectId", project.getId(),
                "name", project.getName(), "type", project.getType());
        titanGraph.tx().commit();
        return vertex;
    }

    public Optional<Vertex> getProjectNodeById(Project project) {
        GraphTraversal<Vertex, Vertex> projectNodeAfterTraversal = titanGraph.traversal().V().has("Project",
                "projectId", project.getId());
        return Optional.ofNullable(projectNodeAfterTraversal.hasNext() ? projectNodeAfterTraversal.next() : null);
    }
}
