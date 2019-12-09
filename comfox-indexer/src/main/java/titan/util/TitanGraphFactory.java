package titan.util;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import config.ConfigManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class TitanGraphFactory {
    private static TitanGraph titanGraph;
    private static String DB_CONFIG_FILE = "db.config";

    public static TitanGraph getTitanFactoryInstance() {
        if (titanGraph == null)
            openTitanGraph();
        return titanGraph;
    }

    private static void openTitanGraph() {
        try {
            Map<String, String> configMap = getConfigMap();
            String propertiesFileLocation = configMap.get("TITAN_DB_CONFIG_FILE");
            titanGraph = TitanFactory.open(propertiesFileLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> getConfigMap() {
        Map<String, String> configMap = new HashMap<>();
        try {
            Path dbConfigFile = Paths.get(DB_CONFIG_FILE);
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

    public static void closeTitanGraph() {
        titanGraph.close();
    }
}
