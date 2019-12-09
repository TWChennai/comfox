package parser;

import com.google.gson.*;
import model.edge.Assignment;
import model.vertex.Person;
import parser.deserializer.AssignmentDeserializer;
import parser.deserializer.PersonDeserializer;
import model.edge.SkillRating;
import parser.deserializer.SkillRatingDeserializer;

import java.util.ArrayList;
import java.util.List;

public class GsonParser<T> extends Parser<T> {
    private Gson gson;

    public GsonParser() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        addCustomDeserializers(gsonBuilder);
        this.gson = gsonBuilder.create();
    }

    public void addCustomDeserializers(GsonBuilder gsonBuilder){
        gsonBuilder.registerTypeAdapter(Assignment.class, new AssignmentDeserializer());
        gsonBuilder.registerTypeAdapter(Person.class, new PersonDeserializer());
        gsonBuilder.registerTypeAdapter(SkillRating.class, new SkillRatingDeserializer());
    }

    @Override
    public String toJson(Object object) {
        return gson.toJson(object);
    }

    @Override
    public T fromJson(String jsonMessage, Class<T> classOfT) {
        return gson.fromJson(jsonMessage, classOfT);
    }

    @Override
    public List<T> fromJsonArray(String jsonMessage, Class<T> classOfT) {
        List<T> items = new ArrayList<>();

        JsonParser jsonParser = new JsonParser();
        JsonArray assignmentsArray = jsonParser.parse(jsonMessage).getAsJsonArray();
        for(JsonElement jsonElement : assignmentsArray) {
            items.add(gson.fromJson(jsonElement, classOfT));
        }

        return items;
    }
}
