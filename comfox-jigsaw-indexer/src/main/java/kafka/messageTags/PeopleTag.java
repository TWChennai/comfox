package kafka.messageTags;

import model.vertex.Person;
import parser.GsonParser;
import parser.Parser;
import service.PersonService;
import titan.util.TitanGraphFactory;

import java.util.List;

public class PeopleTag implements MessageTag {

    private PersonService personService;

    public PeopleTag(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void execute(String tag, String jsonData) {

        Parser<Person> personParser = new GsonParser<>();

        //Add array of person json response into graphDB.
        List<Person> persons = personParser.fromJsonArray(jsonData, Person.class);
        personService.addListOfPersonNodes(persons);

        TitanGraphFactory.closeTitanGraph();
    }
}
