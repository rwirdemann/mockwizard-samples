package sampleservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/samples")
@Produces(MediaType.APPLICATION_JSON)
public class SampleResource {

    private Gateway partnerService;

    public SampleResource(Gateway partnerService) {
        this.partnerService = partnerService;
    }

    @GET
    @Path("/foo")
    public Response foo(@QueryParam(value = "string") String s,
                        @QueryParam(value = "integer") Integer i,
                        @QueryParam(value = "boolean") Boolean b,
                        @QueryParam(value = "double") Double d) {
        int result;
        if (s != null && i != null) {
            result = partnerService.foo(s, i);
        } else if (s != null) {
            result = partnerService.foo(s);
        } else if (i != null) {
            result = partnerService.foo(i);
        } else if (b != null) {
            result = partnerService.foo(b);
        } else if (d != null) {
            result = partnerService.foo(d);
        } else {
            result = partnerService.foo();
        }
        return Response.ok(result).build();
    }

    @GET
    @Path("/fooWithAnyObject")
    public Response fooWithAnyObject() {
        return Response.ok(partnerService.foo(new SampleObject())).build();
    }
}