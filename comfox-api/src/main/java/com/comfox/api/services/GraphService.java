package com.comfox.api.services;

import com.comfox.api.ComfoxApiConfiguration;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;

public class GraphService {
    private static TitanGraph titanGraph;

    private GraphService(ComfoxApiConfiguration configuration) {
        titanGraph = TitanFactory.open(configuration.getTitanPropertiesFileLocation());
    }

    public void closeDb() {
        titanGraph.close();
    }

    public static TitanGraph getTitanGraph(ComfoxApiConfiguration configuration) {
        if (titanGraph == null) {
            new GraphService(configuration);
        }
        return titanGraph;
    }
}