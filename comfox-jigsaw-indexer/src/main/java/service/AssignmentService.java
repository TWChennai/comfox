package service;

import model.edge.Assignment;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.List;
import java.util.Map;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.out;

public class AssignmentService extends GraphService {
    private PersonService personService;
    private ProjectService projectService;

    public AssignmentService(PersonService personService, ProjectService projectService) {
        this.personService = personService;
        this.projectService = projectService;
    }

    public void addListOfAssignedToEdges(List<Assignment> assignments){
        Vertex personNode, projectNode;
        for(Assignment assignment: assignments){
            personNode = personService.getPersonNodeById(assignment.getConsultant()).
                    orElse(personService.createPersonNode(assignment.getConsultant()));

            projectNode = projectService.getProjectNodeById(assignment.getProject()).
                    orElse(projectService.createProjectNode(assignment.getProject()));

            addConsultantAssignedToProjectEdge(personNode, projectNode, assignment);
            titanGraph.tx().commit();
        }
    }

    private void addConsultantAssignedToProjectEdge(Vertex consultantNode, Vertex projectNode, Assignment assignment) {
        consultantNode.addEdge("ASSIGNED_TO", projectNode, "assignmentId", assignment.getAssignmentId(),
                "staffingRequest", assignment.getStaffingRequest(), "effort", assignment.getEffort(),
                "shadow", assignment.getShadow(), "accountName", assignment.getAccountName(),
                "startsOn", assignment.getDuration().getStartsOn(), "endsOn", assignment.getDuration().getEndsOn());
    }

    public void findNumberOfAssignmentsForConsultants(){
        GraphTraversal<Vertex, Map<Object, Object>> assignedToCount = titanGraph.traversal().V().
                hasLabel("Consultant").group().by("employeeId").by(out("ASSIGNED_TO").count());
        while(assignedToCount.hasNext()){
            Map<Object, Object> next = assignedToCount.next();
            System.out.println(next);
        }
    }
}
