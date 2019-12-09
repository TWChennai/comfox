package example;

import model.edge.Assignment;
import parser.GsonParser;
import parser.Parser;
import service.AssignmentService;
import service.PersonService;
import service.ProjectService;
import titan.util.TitanGraphFactory;

import java.util.List;

public class AssignmentDemo {
    public static void main(String[] args) {

        PersonService personService = new PersonService();
        ProjectService projectService = new ProjectService();
        AssignmentService assignmentService = new AssignmentService(personService, projectService);

        Parser<Assignment> assignmentParser = new GsonParser<>();


        //Add array of assignment json response into graphDB.
        //Provide a sample JigsawAPI Assignment Response
        String assignmentJsonMessage = "";
        List<Assignment> assignments = assignmentParser.fromJsonArray(assignmentJsonMessage, Assignment.class);
        assignmentService.addListOfAssignedToEdges(assignments);

        TitanGraphFactory.closeTitanGraph();
    }
}
