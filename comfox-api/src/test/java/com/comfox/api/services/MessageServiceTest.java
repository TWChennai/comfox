//import com.comfox.api.ComfoxApiApplication;
//import com.comfox.api.ComfoxApiConfiguration;
//import com.comfox.api.models.ComfoxMessage;
//import com.comfox.api.services.GraphService;
//import com.comfox.api.services.MessageService;
//import com.thinkaurelius.titan.core.TitanGraph;
//import io.dropwizard.testing.ResourceHelpers;
//import io.dropwizard.testing.junit.DropwizardAppRule;
//import org.apache.tinkerpop.gremlin.structure.T;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.ClassRule;
//import org.junit.Test;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
//
//
//public class MessageServiceTest {
//    private MessageService messageService;
//    private TitanGraph titanGraph;
//
//    @ClassRule
//    public static final DropwizardAppRule<ComfoxApiConfiguration> RULE =
//            new DropwizardAppRule<>(ComfoxApiApplication.class, ResourceHelpers.resourceFilePath("comfox-api-test.yml"));
//
//// move it to BeforeClass
//    @Before
//    public void setUp() throws Exception {
//        titanGraph = GraphService.getTitanGraph(RULE.getConfiguration());
//        messageService = new MessageService(titanGraph);
//    }
//
//    @Test
//    public void shouldGetRecentSlackMessagesFromDb() throws Exception {
//        setUpDataInTitanDB(titanGraph);
//
//        List<ComfoxMessage> recentSlackMessages = messageService.getRecentSlackAndTwitterMessages();
//
//        assertThat(recentSlackMessages.size(), is(2));
//        for (ComfoxMessage comfoxMessage : recentSlackMessages) {
//            assertThat(comfoxMessage.getContent(), is("@comfox latest message"));
//        }
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        titanGraph.traversal().V().drop().iterate();
//        titanGraph.tx().commit();
//    }
//
//    private void setUpDataInTitanDB(TitanGraph titanGraph) {
//        titanGraph.addVertex(T.label, "message", "content", "@comfox latest message", "useremail", "test@test.com", "imageurl", "http://testurl.com", "username", "test", "timestamp", String.valueOf(LocalDateTime.now()));
//        titanGraph.addVertex(T.label, "message", "content", "@comfox latest message", "useremail", "test@test.com", "imageurl", "http://testurl.com", "username", "test", "timestamp", String.valueOf(LocalDateTime.now().minusMinutes(30)));
//        titanGraph.addVertex(T.label, "message", "content", "@comfox edge case message", "useremail", "test@test.com", "imageurl", "http://testurl.com", "username", "test", "timestamp", String.valueOf(LocalDateTime.now().minusHours(1)));
//        titanGraph.addVertex(T.label, "message", "content", "@comfox content2", "useremail", "test@test.com", "imageurl", "http://testurl.com", "username", "test", "timestamp", String.valueOf(LocalDateTime.now().minusHours(2)));
//        titanGraph.tx().commit();
//    }
//}