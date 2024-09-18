import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
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
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        // When - Then
        assertThrows(NoSuchElementException.class, () -> shopService.addOrder(productsIds));

    }

    @Test
    void findOrders_whenStatusProcessing_expect3Orders() {
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        shopService.addOrder(productsIds);
        shopService.addOrder(productsIds);
        shopService.addOrder(productsIds);

        List<Order> result = shopService.findOrders(OrderStatus.PROCESSING);
        assertEquals(3, result.size());

    }

    @Test
    void findOrders_whenStatusCompleted_expect0Orders() {
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        shopService.addOrder(productsIds);
        shopService.addOrder(productsIds);
        shopService.addOrder(productsIds);

        List<Order> result = shopService.findOrders(OrderStatus.COMPLETED);
        assertEquals(0, result.size());

    }
}
