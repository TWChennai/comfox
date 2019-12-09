package parser.deserializer;

import com.google.gson.*;
import model.json.PersonJson;
import model.vertex.Person;

import java.lang.reflect.Type;

public class PersonDeserializer implements JsonDeserializer<Person> {

    @Override
    public Person deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        PersonJson personJson = new Gson().fromJson(jsonElement, PersonJson.class);

        Person person = new Person(personJson.employeeId, personJson.loginName, personJson.preferredName,
                personJson.gender, personJson.picture.url, personJson.role.name, personJson.grade.name,
                personJson.department.name, personJson.hireDate, personJson.totalExperience, personJson.twExperience,
                personJson.assignable, personJson.homeOffice.name, personJson.workingOffice.name,
                personJson.projectPreferences, personJson.longTermGoal, personJson.travelPreferences);

        return person;
    }
}
