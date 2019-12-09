package config;

import java.io.BufferedReader;
import java.io.InputStreamReader;;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ConfigManager {
    private static String JIGSAW_CONFIG_FILE = "jigsaw.properties";
    private static String SLACK_CONFIG_FILE = "slack.properties";
    private static Map<String, String> JIGSAW_CONFIG_MAP;
    private static Map<String, String> SLACK_CONFIG_MAP;

    static {
        JIGSAW_CONFIG_MAP = getConfigMap(JIGSAW_CONFIG_FILE, JIGSAW_CONFIG_MAP);
        SLACK_CONFIG_MAP = getConfigMap(SLACK_CONFIG_FILE,SLACK_CONFIG_MAP);
    }

    public static String getJigsawProperty(String propertyKey) {
        return getConfigMap(JIGSAW_CONFIG_FILE, JIGSAW_CONFIG_MAP).get(propertyKey);
    }

    public static String getSlackProperty(String propertyKey) {
        return getConfigMap(SLACK_CONFIG_FILE,SLACK_CONFIG_MAP).get(propertyKey);
    }

    public static Map<String, String> getConfigMap(String configFile,Map<String,String > CONFIG_MAP) {
        if (CONFIG_MAP != null)
            return CONFIG_MAP;

        Map<String, String> configMap = new HashMap<>();
        try {
            Path dbConfigFile = Paths.get(configFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(dbConfigFile)));
            String line;
            while ((line = reader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, "=");
                configMap.put(st.nextToken().trim(), st.nextToken().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configMap;
    }
}
