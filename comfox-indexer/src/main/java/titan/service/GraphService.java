package titan.service;

import com.thinkaurelius.titan.core.TitanGraph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import titan.util.TitanGraphFactory;

public class GraphService {
    public TitanGraph titanGraph;

    public GraphService() {
        this.titanGraph = TitanGraphFactory.getTitanFactoryInstance();
    }

    public void closeDb() {
        TitanGraphFactory.closeTitanGraph();
    }

    public Boolean isRelated(Vertex vertex1, Vertex vertex2){
        return titanGraph.traversal()
                .V(vertex1)
                .inE("related_to")
                .outV().hasLabel(vertex2.label())
                .has("name", vertex2.property("name").value().toString())
                .hasNext();
    }
}
