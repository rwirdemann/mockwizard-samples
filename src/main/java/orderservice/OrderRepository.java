package orderservice;

import org.mockwizard.examples.orderservice.Order;

import java.util.*;

public class OrderRepository {
    private Map<String, Order> orders = new HashMap<String, Order>();

    public List<Order> find() {
        return new ArrayList<Order>(orders.values());
    }

    public void delete() {
        orders.clear();
    }

    public void create(Order order) {
        order.set_id(UUID.randomUUID().toString());
        orders.put(order.get_id(), order);
    }

    public Order find(String orderId) {
        return orders.get(orderId);
    }
}
