package orderservice.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import orderservice.clearingsystem.ClearingService;
import orderservice.quoteservice.QuoteService;
import org.mockwizard.CollaboratorFactory;

public class OrderServiceConfiguration extends Configuration {

    @JsonProperty("quoteService")
    public CollaboratorFactory<QuoteService> quoteServiceFactory;

    @JsonProperty("clearingService")
    public CollaboratorFactory<ClearingService> clearingServiceFactory;
}
