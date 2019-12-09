package kafka.messageTags;

import model.edge.Assignment;
import parser.GsonParser;
import parser.Parser;
import service.AssignmentService;
import titan.util.TitanGraphFactory;

import java.util.List;

public class AssignmentsTag implements MessageTag {
    private AssignmentService assignmentService;

    public AssignmentsTag(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @Override
    public void execute(String tag, String jsonData) {
        Parser<Assignment> assignmentParser = new GsonParser<>();

        List<Assignment> assignments = assignmentParser.fromJsonArray(jsonData, Assignment.class);

        assignmentService.addListOfAssignedToEdges(assignments);

        TitanGraphFactory.closeTitanGraph();
    }
}
