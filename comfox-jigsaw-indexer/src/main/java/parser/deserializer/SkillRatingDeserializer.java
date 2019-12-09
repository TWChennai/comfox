package parser.deserializer;

import com.google.gson.*;
import model.edge.SkillRating;
import model.json.SkillJson;
import model.vertex.Skill;

import java.lang.reflect.Type;

public class SkillRatingDeserializer implements JsonDeserializer<SkillRating> {

    @Override
    public SkillRating deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        SkillJson skillJson = new Gson().fromJson(json, SkillJson.class);

        Skill skill = new Skill(skillJson.name, skillJson.group);
        SkillRating skillRating = new SkillRating(skill, skillJson.rating);

        return skillRating;
    }
}
