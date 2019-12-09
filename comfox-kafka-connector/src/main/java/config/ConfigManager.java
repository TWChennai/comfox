package config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ConfigManager {
    private static String JIGSAW_CONFIG_FILE = "jigsaw.properties";
    private static Map<String, String> CONFIG_MAP;

    static {
        CONFIG_MAP = getConfigMap();
    }

    public static String getProperty(String propertyKey) {
        return getConfigMap().get(propertyKey);
    }

    private static Map<String, String> getConfigMap() {
        if(CONFIG_MAP != null)
            return CONFIG_MAP;

        Map<String, String> configMap = new HashMap<>();
        try {
            Path dbConfigFile = Paths.get(JIGSAW_CONFIG_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(dbConfigFile)));
            String line;
            while((line = reader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, "=");
                configMap.put(st.nextToken().trim(), st.nextToken().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configMap;
    }
}
