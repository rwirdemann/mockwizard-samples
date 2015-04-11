package orderservice.application;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import orderservice.OrderRepository;
import orderservice.OrderResource;
import orderservice.clearingsystem.ClearingService;
import orderservice.quoteservice.QuoteService;
import org.mockwizard.Mockwizard;

public class OrderServiceApplication extends Application<OrderServiceConfiguration> {

    @Override
    public void initialize(Bootstrap<OrderServiceConfiguration> quoteServiceConfigurationBootstrap) {
    }

    @Override
    public void run(OrderServiceConfiguration configuration, Environment environment) throws Exception {
        Mockwizard.init(environment);
        environment.jersey().register(new OrderResource(new OrderRepository(),
                configuration.quoteServiceFactory.service(QuoteService.class),
                configuration.clearingServiceFactory.service(ClearingService.class)));
    }

    public static void main(String[] args) throws Exception {
        new OrderServiceApplication().run(args);
    }
}
