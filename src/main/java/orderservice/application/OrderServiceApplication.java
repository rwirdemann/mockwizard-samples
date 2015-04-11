package orderservice.application;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.mockwizard.Mockwizard;
import org.mockwizard.examples.DummyHealthCheck;
import org.mockwizard.examples.orderservice.OrderRepository;
import org.mockwizard.examples.orderservice.OrderResource;
import org.mockwizard.examples.orderservice.application.OrderServiceConfiguration;
import org.mockwizard.examples.orderservice.clearingsystem.ClearingService;
import org.mockwizard.examples.orderservice.quoteservice.QuoteService;

public class OrderServiceApplication extends Application<org.mockwizard.examples.orderservice.application.OrderServiceConfiguration> {

    @Override
    public void initialize(Bootstrap<org.mockwizard.examples.orderservice.application.OrderServiceConfiguration> quoteServiceConfigurationBootstrap) {
    }

    @Override
    public void run(OrderServiceConfiguration configuration, Environment environment) throws Exception {
        Mockwizard.init(environment);
        environment.jersey().register(new OrderResource(new OrderRepository(),
                configuration.quoteServiceFactory.service(QuoteService.class),
                configuration.clearingServiceFactory.service(ClearingService.class)));
        environment.healthChecks().register("dummy_health_check", new DummyHealthCheck());
    }

    public static void main(String[] args) throws Exception {
        new org.mockwizard.examples.orderservice.application.OrderServiceApplication().run(args);
    }
}
