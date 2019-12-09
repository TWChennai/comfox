package jigsaw.json.util;


import org.json.JSONArray;

public class JsonUtils {
    public static String mergeJsonArrays(String jsonResponse1, String jsonResponse2) {
        StringBuilder jsonStringBuilder = new StringBuilder();
        jsonStringBuilder.append(jsonResponse1.substring(0, jsonResponse1.lastIndexOf(']')));

        if (!isJsonArrayEmpty(jsonResponse1) && !isJsonArrayEmpty(jsonResponse2))
            jsonStringBuilder.append(",");

        jsonStringBuilder.append(jsonResponse2.substring(jsonResponse2.indexOf('[') + 1));
        return jsonStringBuilder.toString();
    }

    public static boolean isJsonArrayEmpty(String jsonResponse) {
        JSONArray jsonArray = new JSONArray(jsonResponse);
        return jsonArray.length() == 0;
    }
}

