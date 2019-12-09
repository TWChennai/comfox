package jigsaw;

import com.google.gson.Gson;
import model.jigsaw.Assignment;
import model.jigsaw.Person;
import titan.service.jigsaw.JigsawAssignmentService;
import titan.service.jigsaw.JigsawPersonService;
import titan.service.jigsaw.JigsawProjectService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class JigsawDemo {
    private JigsawHttpClient jigsawHttpClient;
    private Gson gson;
    private JigsawPersonService jigsawPersonService;


    public JigsawDemo(JigsawHttpClient jigsawHttpClient, Gson gson, JigsawPersonService jigsawPersonService) {
        this.jigsawHttpClient = jigsawHttpClient;
        this.gson = gson;
        this.jigsawPersonService = jigsawPersonService;
    }

    public void persistJigsawPeople(List<Person> persons) {
        persons.forEach(jigsawPersonService::createPersonNode);
    }

    public void persistJigsawAssignments(List<Assignment> assignments) {
        JigsawAssignmentService jigsawAssignmentService = new JigsawAssignmentService(jigsawPersonService, new JigsawProjectService());
        jigsawAssignmentService.addListOfAssignedToEdges(assignments);
    }

    public List<Person> getJigsawPeopleData() throws IOException, URISyntaxException {
        JigsawCrawler jigsawCrawler = new JigsawCrawler(jigsawHttpClient, gson);
        return jigsawCrawler.getJigsawPeopleData();
    }

    public List<Assignment> getJigsawAssignmentsData() throws IOException, URISyntaxException {
        JigsawCrawler jigsawCrawler = new JigsawCrawler(jigsawHttpClient, gson);
        return jigsawCrawler.getJigsawAssignmentsData();
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        JigsawDemo jigsawDemo = new JigsawDemo(new JigsawHttpClient(), new Gson(), new JigsawPersonService());
        List<Person> persons = jigsawDemo.getJigsawPeopleData();
        jigsawDemo.persistJigsawPeople(persons);
        List<Assignment> jigsawAssignmentsData = jigsawDemo.getJigsawAssignmentsData();
        jigsawDemo.persistJigsawAssignments(jigsawAssignmentsData);
    }

}
