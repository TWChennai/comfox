package titan.service.jigsaw;


import model.jigsaw.Consultant;
import model.jigsaw.Person;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import titan.service.GraphService;
import titan.service.MessageService;

import java.util.Optional;

public class JigsawPersonService extends GraphService {

    public void createPersonNode(Person person) {
        Vertex v = titanGraph.addVertex(T.label, "person", "employeeId", person.getEmployeeId(),
                "loginName", person.getLoginName(), "name", person.getPreferredName(),
                "gender", person.getGender().toString(), "role", person.getRole(),
                "grade", person.getGrade(), "department", person.getDepartment(), "hireDate", person.getHireDate(),
                "totalExperience", person.getTotalExperience(), "twExperience", person.getTwExperience(),
                "assignable", person.getAssignable(), "homeOffice", person.getHomeOffice(),
                "workingOffice", person.getWorkingOffice(), "projectPreferences", person.getProjectPreferences(),
                "longTermGoal", person.getLongTermGoal(), "domesticTravelPreference", person.getTravelPreferences().getDomestic(), "internationalTravelPreferences", person.getTravelPreferences().getInternational(), "travelDetails", person.getTravelPreferences().getTravelDetails());

        titanGraph.tx().commit();
    }

    public Optional<Vertex> getPersonNodeById(Consultant consultant) {
        GraphTraversal<Vertex, Vertex> consultantNodeAfterTraversal = titanGraph.traversal().V().has("person",
                "employeeId", consultant.getEmployeeId());

        return Optional.ofNullable(consultantNodeAfterTraversal.hasNext() ? consultantNodeAfterTraversal.next() : null);
    }

    public Vertex createConsultantNode(Consultant consultant) {
        Vertex v = titanGraph.addVertex(T.label, "consultant", "employeeId", consultant.getEmployeeId(), "name", consultant.getName());
        titanGraph.tx().commit();

        return v;
    }
}
