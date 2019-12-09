package service;

import model.edge.SkillRating;
import model.vertex.Skill;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.apache.tinkerpop.gremlin.process.traversal.P.neq;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.inE;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.out;

public class SkillService extends GraphService {

    public void addSkillsForPerson(List<SkillRating> skillRatings, Vertex personNode) {
        Vertex skillNode;
        for(SkillRating skillRating: skillRatings) {
            if(Integer.parseInt(skillRating.getRating()) > 0) {
                skillNode = getSkillNode(skillRating.getSkill()).orElse(createSkillNode(skillRating.getSkill()));
                addPersonKnowsSkillEdge(personNode, skillNode, skillRating);
            }
            titanGraph.tx().commit();
        }
    }

    private void addPersonKnowsSkillEdge(Vertex personNode, Vertex skillNode, SkillRating skillRating) {
        personNode.addEdge("KNOWS", skillNode, "rating", skillRating.getRating());
    }

    private Vertex createSkillNode(Skill skill) {
        Vertex skillNode = titanGraph.addVertex(T.label, "Skill", "name", skill.getName(),
                "group", skill.getGroup().getName());
        titanGraph.tx().commit();
        return skillNode;
    }

    public Optional<Vertex> getSkillNode(Skill skill){
        GraphTraversal<org.apache.tinkerpop.gremlin.structure.Vertex, org.apache.tinkerpop.gremlin.structure.Vertex>
                skillNodeAfterTraversal = titanGraph.traversal().V().has("Skill", "name", skill.getName());

        return Optional.ofNullable(skillNodeAfterTraversal.hasNext() ? skillNodeAfterTraversal.next() : null);
    }

    public void findCountForEachSkillInEachProject(){
        GraphTraversal<Vertex, Map<Object, Object>> skilledCount = titanGraph.traversal().V().
                hasLabel("Skill").group().by("name").by(inE("KNOWS").outV().
                out("ASSIGNED_TO").groupCount().by("projectId"));

        while(skilledCount.hasNext()){
            System.out.println(skilledCount.next());
        }
    }

    public void findNumOfCommonSkillsForPairsWhoShareAtLeastOneSkill(){
        GraphTraversal<Vertex, Map<Object, Object>> commonSkillsCount = titanGraph.traversal().V().
                hasLabel("Person").as("me").group().by("employeeId").by(out("KNOWS").aggregate("mySkills")
                .in("KNOWS").where(neq("me")).as("other").groupCount().by("employeeId"));

        while(commonSkillsCount.hasNext()){
            System.out.println(commonSkillsCount.next());
        }
    }
}
