package example;

import model.edge.SkillRating;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import parser.GsonParser;
import parser.Parser;
import service.PersonService;
import service.SkillService;
import titan.util.TitanGraphFactory;

import java.util.List;

public class SkillDemo {
    public static void main(String[] args) {
        new SkillDemo().addSkills();
        //new SkillService().findNumOfCommonSkillsForPairsWhoShareAtLeastOneSkill();

        TitanGraphFactory.closeTitanGraph();
    }

    public void addSkills(){
        Parser<SkillRating> parser = new GsonParser<>();

        String jsonMessage = "";
        List<SkillRating> skillRatings = parser.fromJsonArray(jsonMessage, SkillRating.class);

        String employeeId = "";
        Vertex personNode = (new PersonService().getPersonNodeWithEmployeeId(employeeId)).get();
        SkillService skillService = new SkillService();
        skillService.addSkillsForPerson(skillRatings, personNode);

    }
}
