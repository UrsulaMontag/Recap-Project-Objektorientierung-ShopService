import lombok.With;

import java.time.Instant;
import java.util.List;

@With
public record Order(
        String id,
        List<Product> products,
        OrderStatus orderStatus,
        Instant timestamp
) {

    public Order(String id, List<Product> products, OrderStatus orderStatus, Instant timestamp) {
        this.id = id;
        this.products = products;
        this.orderStatus = orderStatus;
        this.timestamp = timestamp;
    }

    public Order(String id, List<Product> products, OrderStatus orderStatus) {
        this(id, products, orderStatus, Instant.now());
    }

}
