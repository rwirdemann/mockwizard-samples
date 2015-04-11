package orderservice.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.mockwizard.CollaboratorFactory;
import org.mockwizard.examples.orderservice.clearingsystem.ClearingService;
import org.mockwizard.examples.orderservice.quoteservice.QuoteService;

public class OrderServiceConfiguration extends Configuration {

    @JsonProperty("quoteService")
    public CollaboratorFactory<QuoteService> quoteServiceFactory;

    @JsonProperty("clearingService")
    public CollaboratorFactory<ClearingService> clearingServiceFactory;
}
