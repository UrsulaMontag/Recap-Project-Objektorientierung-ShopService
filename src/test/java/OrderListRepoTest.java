import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderListRepoTest {

    @Test
    void getOrders() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();

        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product), OrderStatus.PROCESSING);
        repo.addOrder(newOrder);

        //WHEN
        List<Order> actual = repo.getOrders();

        //THEN
        List<Order> expected = new ArrayList<>();
        Product product1 = new Product("1", "Apfel");
        expected.add(new Order("1", List.of(product1), OrderStatus.PROCESSING));

        assertThat(actual)
                // compare while ignoring timestamps
                .usingRecursiveComparison()
                .ignoringFields("timestamp")
                .ignoringAllOverriddenEquals()
                .isEqualTo(expected);
    }

    @Test
    void getOrderById() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();

        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product), OrderStatus.PROCESSING);
        repo.addOrder(newOrder);

        //WHEN
        Order actual = repo.getOrderById("1").orElseThrow();

        //THEN
        Product product1 = new Product("1", "Apfel");
        Order expected = new Order("1", List.of(product1), OrderStatus.PROCESSING);

        assertThat(actual)
                // compare while ignoring timestamps
                .usingRecursiveComparison()
                .ignoringFields("timestamp")
                .ignoringAllOverriddenEquals()
                .isEqualTo(expected);
    }

    @Test
    void addOrder() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();
        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product), OrderStatus.PROCESSING);

        //WHEN
        Order actual = repo.addOrder(newOrder);

        //THEN
        Product product1 = new Product("1", "Apfel");
        Order expected = new Order("1", List.of(product1), OrderStatus.PROCESSING);
        assertThat(actual)
                // compare while ignoring timestamps
                .usingRecursiveComparison()
                .ignoringFields("timestamp")
                .ignoringAllOverriddenEquals()
                .isEqualTo(expected);
        assertThat(repo.getOrderById("1").orElseThrow())
                // compare while ignoring timestamps
                .usingRecursiveComparison()
                .ignoringFields("timestamp")
                .ignoringAllOverriddenEquals()
                .isEqualTo(expected);
    }

    @Test
    void removeOrder() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();

        //WHEN
        repo.removeOrder("1");

        //THEN
        assertFalse(repo.getOrderById("1").isPresent());
    }
}
