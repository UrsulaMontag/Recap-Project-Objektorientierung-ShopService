import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {
    private static ShopService shopService;

    @BeforeEach
    void setup() {
        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();
        shopService = new ShopService(productRepo, orderRepo);
    }

    @Test
    void addOrderTest() {
        //GIVEN
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING);
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNoSuchElementException() {
        //GIVEN
        List<String> productsIds = List.of("1", "2");

        // When - Then
        assertThrows(NoSuchElementException.class, () -> shopService.addOrder(productsIds));

    }

    @Test
    void findOrders_whenStatusProcessing_expect3Orders() {
        List<String> productsIds = List.of("1");
        shopService.addOrder(productsIds);
        shopService.addOrder(productsIds);
        shopService.addOrder(productsIds);

        List<Order> result = shopService.findOrders(OrderStatus.PROCESSING);
        assertEquals(3, result.size());

    }

    @Test
    void findOrders_whenStatusCompleted_expect0Orders() {
        List<String> productsIds = List.of("1");
        shopService.addOrder(productsIds);
        shopService.addOrder(productsIds);
        shopService.addOrder(productsIds);

        List<Order> result = shopService.findOrders(OrderStatus.COMPLETED);
        assertEquals(0, result.size());

    }

    @Test
    void updateOrder_whenIdAndStatusGiven_expectOrderWithIdAndUpdatedStatus() {
        // Given
        List<String> productsIds = List.of("1");
        Order order = shopService.addOrder(productsIds);

        // When
        shopService.updateOrder(order.id(), OrderStatus.COMPLETED);

        // Then
        List<Order> processingOrders = shopService.findOrders(OrderStatus.PROCESSING);
        assertEquals(0, processingOrders.size());
        List<Order> completedOrders = shopService.findOrders(OrderStatus.COMPLETED);
        assertEquals(1, completedOrders.size());

    }

    @Test
    void updateOrder_whenWrongIdAndStatusGiven_expectNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> shopService.updateOrder("-1", OrderStatus.COMPLETED));
    }
}
