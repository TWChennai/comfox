package kafka.messageTags;

import model.edge.SkillRating;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import parser.GsonParser;
import parser.Parser;
import service.PersonService;
import service.SkillService;
import titan.util.TitanGraphFactory;

import java.util.List;

public class SkillsTag implements MessageTag {
    private PersonService personService;
    private SkillService skillService;

    private static final int startingIndexOfEmployeeIdInSkillsTag = new String("skills").length();

    public SkillsTag(PersonService personService, SkillService skillService) {
        this.personService = personService;
        this.skillService = skillService;
    }

    @Override
    public void execute(String tag, String jsonData) {
        Parser<SkillRating> parser = new GsonParser<>();
        List<SkillRating> skillRatings = parser.fromJsonArray(jsonData, SkillRating.class);

        String employeeId = tag.substring(startingIndexOfEmployeeIdInSkillsTag);
        Vertex personNode = (new PersonService().getPersonNodeWithEmployeeId(employeeId)).get();
        skillService.addSkillsForPerson(skillRatings, personNode);

        TitanGraphFactory.closeTitanGraph();
    }
}
