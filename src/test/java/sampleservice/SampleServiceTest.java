package sampleservice;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockwizard.Mockwizard;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class SampleServiceTest {

    @ClassRule
    public static final DropwizardAppRule<SampleServiceConfiguration> RULE =
            new DropwizardAppRule<SampleServiceConfiguration>(SampleServiceApplication.class, resourceFilePath("sample-service-config.yml"));

    private SampleClient sampleClient = new SampleClient(RULE.getLocalPort());

    @BeforeClass
    public static void setUpClass() throws Exception {
        Mockwizard.setup(RULE.getLocalPort());
    }

    @Test
    public void mockNoParameter() throws Exception {
        Mockwizard.when("gateway.foo").thenReturn(1);
        assertEquals(1, sampleClient.foo());
    }

    @Test
    public void mockWithString() throws Exception {
        Mockwizard.when("gateway.foo").with("hello").thenReturn(2);
        Mockwizard.when("gateway.foo").with("hallo").thenReturn(3);

        assertEquals(2, sampleClient.foo("hello"));
        assertEquals(3, sampleClient.foo("hallo"));
    }

    @Test
    public void mockWithInteger() throws Exception {
        Mockwizard.when("gateway.foo").with(3).thenReturn(3);
        assertEquals(3, sampleClient.foo(3));
    }

    @Test
    public void mockWithBoolean() throws Exception {
        Mockwizard.when("gateway.foo").with(true).thenReturn(7);
        assertEquals(7, sampleClient.foo(true));
    }

    @Test
    public void mockWithDouble() throws Exception {
        Mockwizard.when("gateway.foo").with(22.24).thenReturn(8);
        assertEquals(8, sampleClient.foo(22.24));
        assertEquals(0, sampleClient.foo(12.13));
    }

    @Test
    public void mockWithAnyObject() throws Exception {
        Mockwizard.when("gateway.foo").with(SampleObject.class).thenReturn(9);
        assertEquals(9, sampleClient.fooWithAnyObject());
    }

    @Test
    public void mockWithTwoParameter() throws Exception {
        Mockwizard.when("gateway.foo").with("hello").with(4).thenReturn(4);
        assertEquals(4, sampleClient.foo("hello", 4));
    }

    private static String resourceFilePath(String s) {
        try {
            return new File(Resources.getResource(s).toURI()).getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static class SampleClient {
        private final String baseUri;
        private Client client = new Client();

        public SampleClient(int localPort) {
            baseUri = "http://localhost:" + localPort;
        }

        int foo() {
            WebResource resource = client.resource(baseUri).path("samples/foo");
            ClientResponse clientResponse = resource.get(ClientResponse.class);
            return clientResponse.getEntity(Integer.class);
        }

        public int foo(String s) {
            WebResource resource = client.resource(baseUri).path("samples/foo").queryParam("string", s);
            ClientResponse clientResponse = resource.get(ClientResponse.class);
            return clientResponse.getEntity(Integer.class);
        }

        public int foo(Integer i) {
            WebResource resource = client.resource(baseUri).path("samples/foo").queryParam("integer", Integer.toString(i));
            ClientResponse clientResponse = resource.get(ClientResponse.class);
            return clientResponse.getEntity(Integer.class);
        }

        public int foo(String s, Integer i) {
            WebResource resource = client.resource(baseUri).path("samples/foo").queryParam("string", s).queryParam("integer", Integer.toString(i));
            ClientResponse clientResponse = resource.get(ClientResponse.class);
            return clientResponse.getEntity(Integer.class);
        }

        public int foo(boolean b) {
            WebResource resource = client.resource(baseUri).path("samples/foo").queryParam("boolean", Boolean.toString(b));
            ClientResponse clientResponse = resource.get(ClientResponse.class);
            return clientResponse.getEntity(Integer.class);
        }

        public int foo(Double d) {
            WebResource resource = client.resource(baseUri).path("samples/foo").queryParam("double", Double.toString(d));
            ClientResponse clientResponse = resource.get(ClientResponse.class);
            return clientResponse.getEntity(Integer.class);
        }

        public int fooWithAnyObject() {
            WebResource resource = client.resource(baseUri).path("samples/fooWithAnyObject");
            ClientResponse clientResponse = resource.get(ClientResponse.class);
            return clientResponse.getEntity(Integer.class);
        }
    }
}
