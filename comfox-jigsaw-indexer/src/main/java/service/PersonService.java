package service;

import model.Consultant;
import model.vertex.Person;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.List;
import java.util.Optional;

public class PersonService extends GraphService {

    public Optional<Vertex> getPersonNodeById(Consultant consultant) {
        GraphTraversal<Vertex, Vertex> consultantNodeAfterTraversal = titanGraph.traversal().V().has("Person",
                "employeeId", consultant.getEmployeeId());

        return Optional.ofNullable(consultantNodeAfterTraversal.hasNext() ? consultantNodeAfterTraversal.next() : null);
    }

    public Optional<Vertex> getPersonNodeWithEmployeeId(String employeeId){
        GraphTraversal<Vertex, Vertex> consultantNodeAfterTraversal = titanGraph.traversal().V().
                has("Person", "employeeId", employeeId);

        return Optional.ofNullable(consultantNodeAfterTraversal.hasNext() ? consultantNodeAfterTraversal.next() : null);
    }

    public Vertex createPersonNode(Consultant consultant) {
        return null;
    }

    public Vertex createPersonNode(Person person) {
        Vertex v = titanGraph.addVertex(T.label, "Person", "employeeId", person.getEmployeeId(),
                "loginName", person.getLoginName(), "preferredName", person.getPreferredName(),
                "gender", person.getGender().toString(), "pictureUrl", person.getPictureUrl(), "role", person.getRole(),
                "grade", person.getGrade(), "department", person.getDepartment(), "hireDate", person.getHireDate(),
                "totalExperience", person.getTotalExperience(), "twExperience", person.getTwExperience(),
                "assignable", person.getAssignable(), "homeOffice", person.getHomeOffice(),
                "workingOffice", person.getWorkingOffice(), "projectPreferences", person.getProjectPreferences(),
                "longTermGoal", person.getLongTermGoal(), "domesticTravelPreference", person.getTravelPreferences().
                        getDomestic(), "internationalTravelPreferences", person.getTravelPreferences().
                        getInternational(), "travelDetails", person.getTravelPreferences().getTravelDetails());

        titanGraph.tx().commit();

        return v;
    }

    public void addListOfPersonNodes(List<Person> persons) {
        persons.forEach(this::createPersonNode);
    }
}
