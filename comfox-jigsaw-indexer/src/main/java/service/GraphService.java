package service;

import com.thinkaurelius.titan.core.TitanGraph;
import titan.util.TitanGraphFactory;

public abstract class GraphService {

    protected TitanGraph titanGraph;

    public GraphService() {
        titanGraph = TitanGraphFactory.getTitanFactoryInstance();
    }

    public void closeDb() {
        TitanGraphFactory.closeTitanGraph();
    }

}
