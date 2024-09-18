import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.*;

@RequiredArgsConstructor
public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId).orElseThrow(() -> new NoSuchElementException("Product not found: " + productId));

            products.add(productToOrder);
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING, Instant.now());

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> findOrders(OrderStatus orderStatus) {
        return orderRepo.getOrders()
                .stream()
                .filter(order -> Objects.equals(order.orderStatus(), orderStatus))
                .toList();
    }

    public void updateOrder(String id, OrderStatus orderStatus) {
        Order orderToUpdate = orderRepo.getOrderById(id).orElseThrow(() -> new NoSuchElementException("Order with id: " + id + " does not exist."));
        orderRepo.removeOrder(orderToUpdate.id());
        orderRepo.addOrder(orderToUpdate.withOrderStatus(orderStatus));
    }

    public Map<OrderStatus, Order> getOldestOrderPerStatus() {
        List<Order> orders = orderRepo.getOrders();
        Map<OrderStatus, Order> oldestOrdersPerStatus = new HashMap<>();

        for (Order order : orders) {
            OrderStatus status = order.orderStatus();
            Order currentOldest = oldestOrdersPerStatus.get(status);

            if (currentOldest == null || order.timestamp().isBefore(currentOldest.timestamp())) {
                oldestOrdersPerStatus.put(status, order);
            }
        }

        return oldestOrdersPerStatus;
    }

}
