package additional;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import orders.Order;
import orders.OrderClient;
import org.junit.Before;
import org.junit.Test;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Epic("Creating new user role")
@Feature("Get order by courier")
public class GetOrderWithIdTest {

    private OrderClient orderClient;
    private int trackId;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Check getting orders by courier")
    @Description("Basic test for get order")
    public void getOrderTest() {
        Order order = new Order ("Кэд", "Бэйн", "Галактическая республика, 2",
                "Автозаводская", "+71236567898", 1, "2022-12-12", "где бобба?");
        trackId = orderClient.orderCreate(order);

        Map<String, ?> response = orderClient.getOrder(trackId);

        assertNotNull(response);
    }

    @Test
    @DisplayName("Check getting orders by courier")
    @Description("Basic test for get order without track id")
    public void getOrderWithoutTrackIdTest() {
        String message = orderClient.getOrderWithoutTrackId();

        assertEquals("Недостаточно данных для поиска", message);
    }

    @Test
    @DisplayName("Check getting orders by courier")
    @Description("Basic test for get order with incorrect track id")
    public void geOrderWithIncorrectIdTest() {
        trackId = 0;

        String message = orderClient.getOrderWithIncorrectTrackId(trackId);

        assertEquals("Заказ не найден", message);
    }
}


