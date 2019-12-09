package data.source.jigsaw;


import kafka.Producer;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class JigsawCrawler {
    private JigsawHttpClient jigsawHttpClient;
    private Producer kafkaProducer;

    private static final String messageTagForPeopleData = "persons";
    private static final String prefixMessageTagForSkillsData = "skills";
    private static final String messageTagForAssignmentsData = "assignments";

    public JigsawCrawler(data.source.jigsaw.JigsawHttpClient jigsawHttpClient, Producer kafkaProducer) {
        this.jigsawHttpClient = jigsawHttpClient;
        this.kafkaProducer = kafkaProducer;
    }

    private void getEmployeeIdsFromPersonsJsonData(String peopleJsonData, List<String> employeeIds) {
        JSONArray peopleJsonArray = new JSONArray(peopleJsonData);
        for(int personIndex = 0; personIndex < peopleJsonArray.length(); personIndex++){
            JSONObject personJsonObject = peopleJsonArray.getJSONObject(personIndex);
            employeeIds.add(personJsonObject.getString("employeeId"));
        }
    }

    public void storePersonsDataInWorkingOfficeInKafka(String workingOffice) throws IOException, URISyntaxException {
        List<String> employeeIds = new ArrayList<>();
        String personsJsonData = jigsawHttpClient.getPersonsInWorkingOffice(workingOffice);
        getEmployeeIdsFromPersonsJsonData(personsJsonData, employeeIds);

        storeMessageInKafkaTopic(messageTagForPeopleData, personsJsonData);
    }

    public void storeSkillsDataForPersonInKafka(String employeeId) throws IOException, URISyntaxException {
        String skillsJsonData = jigsawHttpClient.getSkillsOfPersonWithEmployeeId(employeeId);

        String messageTag = prefixMessageTagForSkillsData + employeeId;
        storeMessageInKafkaTopic(messageTag, skillsJsonData);
    }

    public void storeAssignmentsDataForPersonInKafka(String employeeId) throws IOException, URISyntaxException {
        String assignmentsJsonData = jigsawHttpClient.getAssignmentsOfPersonWithEmployeeId(employeeId);

        storeMessageInKafkaTopic(messageTagForAssignmentsData, assignmentsJsonData);
    }

    private void storeMessageInKafkaTopic(String messageTag, String messageContent){
        kafkaProducer.send(messageTag, messageContent);
    }
}
