package sampleservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.mockwizard.CollaboratorFactory;

public class SampleServiceConfiguration extends Configuration {

    @JsonProperty("gateway")
    public CollaboratorFactory<Gateway> gatewayFactory;
}
