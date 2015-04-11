package orderservice;

import org.mockwizard.examples.orderservice.Order;
import org.mockwizard.examples.orderservice.OrderRepository;
import org.mockwizard.examples.orderservice.clearingsystem.ClearingService;
import org.mockwizard.examples.orderservice.quoteservice.QuoteService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {
    @Context
    private UriInfo uriInfo;

    private org.mockwizard.examples.orderservice.OrderRepository orderRepository;
    private final QuoteService quoteService;
    private final ClearingService clearingService;

    public OrderResource(OrderRepository orderRepository, QuoteService quoteService, ClearingService clearingService) {
        this.orderRepository = orderRepository;
        this.quoteService = quoteService;
        this.clearingService = clearingService;
    }

    @GET
    public List<Order> orders() {
        return orderRepository.find();
    }

    @GET
    @Path("{orderId}")
    public Order get(@PathParam("orderId") String orderId) {
        return orderRepository.find(orderId);
    }

    @POST
    public Response create(Order o) {
        double price = quoteService.getPrice(o.getSymbol());
        if (price <= o.getLimit()) {
            o.setPrice(price);
            orderRepository.create(o);
            clearingService.clear(o);
            UriBuilder ub = uriInfo.getAbsolutePathBuilder();
            URI uri = ub.path(o.get_id()).build();
            return Response.created(uri).build();
        } else {
            return Response.noContent().build();
        }
    }

    @DELETE
    public void delete() {
        orderRepository.delete();
    }

}
