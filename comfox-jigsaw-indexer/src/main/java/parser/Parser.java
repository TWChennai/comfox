package parser;

import java.util.List;

public abstract class Parser<T> {

    public abstract String toJson(Object object);

    public abstract T fromJson(String jsonMessage, Class<T> classOfT);

    public abstract List<T> fromJsonArray(String jsonMessage, Class<T> classOfT);
}
