package orderservice;

import com.google.common.io.Resources;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockwizard.Mockwizard;
import org.mockwizard.examples.VerificationException;
import org.mockwizard.examples.orderservice.Order;
import org.mockwizard.examples.orderservice.application.OrderServiceApplication;
import org.mockwizard.examples.orderservice.application.OrderServiceConfiguration;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class OrderServiceTest {

    @ClassRule
    public static final DropwizardAppRule<OrderServiceConfiguration> RULE =
            new DropwizardAppRule<OrderServiceConfiguration>(OrderServiceApplication.class, resourceFilePath("configuration.yml"));
    private OrderServiceClient orderServiceClient;

    @BeforeClass
    public static void setUpClass() throws Exception {
        Mockwizard.setup(RULE.getLocalPort());
    }

    @Before
    public void setUp() throws Exception {
        orderServiceClient = new OrderServiceClient(RULE.getLocalPort());
        orderServiceClient.delete();

        Mockwizard.reset();
    }

    @Test
    public void shouldUpdateBuyPrice() throws Exception {
        // GIVEN: An actual quote for TSLA
        Mockwizard.when("quoteservice.getPrice").with("TSLA").thenReturn(199.0);

        // WHEN: Order created
        String orderId = orderServiceClient.create(new org.mockwizard.examples.orderservice.Order("TSLA", 5).withLimit(200.0));

        // THEN: The order was bought for the quote of TSLA
        org.mockwizard.examples.orderservice.Order o = orderServiceClient.get(orderId);
        assertEquals(199.0, o.getPrice(), 0.000001);
    }

    @Test
    public void shouldDenyOrder() throws Exception {
        // GIVEN: Limit exceeding order price
        Mockwizard.when("quoteservice.getPrice").with("TSLA").thenReturn(210.0);

        // WHEN: Order requested
        orderServiceClient.create(new org.mockwizard.examples.orderservice.Order("TSLA", 5).withLimit(200.0));

        // THEN: Order was denied
        List<org.mockwizard.examples.orderservice.Order> orders = orderServiceClient.all();
        assertTrue(orders.isEmpty());
    }

    @Test
    public void shouldClearOrder() throws Exception {
        // GIVEN: Limit exceeding order price
        Mockwizard.when("quoteservice.getPrice").with("TSLA").thenReturn(210.0);

        // WHEN: Order requested
        orderServiceClient.create(new Order("TSLA", 5).withLimit(211.0));

        // THEN: Order was cleared
        Mockwizard.verify("clearingservice.clear");
    }

    /**
     * Demonstrates a verification failure.
     */
    @Test
    public void demonstrateFailedVerification() throws Exception {
        try {
            Mockwizard.verify("clearingservice.clear");
            fail();
        } catch (VerificationException e) {
            assertEquals("Wanted but not invoked:\n  clear(...)", e.toString());
        }
    }

    private static String resourceFilePath(String s) {
        try {
            return new File(Resources.getResource(s).toURI()).getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
