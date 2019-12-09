package example;

import model.vertex.Person;
import parser.GsonParser;
import parser.Parser;
import service.PersonService;
import titan.util.TitanGraphFactory;

import java.util.List;

public class PersonDemo {
    public static void main(String[] args) {

        PersonService personService = new PersonService();

        Parser<Person> personParser = new GsonParser<>();

        //Add array of person json response into graphDB.
        //Provide a sample JigsawAPI People Response
        String personJsonMessage = "";
        List<Person> persons = personParser.fromJsonArray(personJsonMessage, Person.class);
        personService.addListOfPersonNodes(persons);

        TitanGraphFactory.closeTitanGraph();
    }
}
