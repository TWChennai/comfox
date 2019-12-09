package parser.deserializer;

import com.google.gson.*;
import model.edge.Assignment;
import model.json.AssignmentJson;

import java.lang.reflect.Type;

public class AssignmentDeserializer implements JsonDeserializer<Assignment> {

    @Override
    public Assignment deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        AssignmentJson assignmentJson = new Gson().fromJson(jsonElement, AssignmentJson.class);

        Assignment assignment = new Assignment(assignmentJson.id, assignmentJson.consultant,
                assignmentJson.staffingRequest.id, assignmentJson.effort, assignmentJson.shadow, assignmentJson.account.name,
                assignmentJson.project, assignmentJson.duration);

        return assignment;
    }

}
