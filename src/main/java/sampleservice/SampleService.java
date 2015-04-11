package sampleservice;

import org.mockwizard.examples.sampleservice.Gateway;

public class SampleService {
    
    private Gateway gateway;
    
    public int foo() {
        return gateway.foo();
    }
}
