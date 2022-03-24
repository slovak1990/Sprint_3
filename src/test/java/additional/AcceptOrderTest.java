package additional;

import couriers.Courier;
import couriers.CourierClient;
import couriers.CourierCredentials;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import orders.Order;
import orders.OrderClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Epic("Creating new user role")
@Feature("Accept order")
public class AcceptOrderTest {

    private CourierClient courierClient;
    private OrderClient orderClient;
    private int courierId;
    private int orderTrack;

    @Before
    public void setUp() { courierClient = new CourierClient();
    orderClient = new OrderClient(); }

    @After
    public void teardown() { courierClient.delete(courierId);}

    @Test
    @DisplayName("Check possibility accepted order")
    @Description("Basic test for accepted order by courier")
    public void acceptOrderByCourierTest() {
        Order order = new Order("Бобба", "Фет",
                     "Галактическая империя, 1", "Сокольники", "+71236547456",
                     7, "2022-04-30", "джедаи будут повержены");
        orderTrack = orderClient.orderCreate(order);

        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));

        boolean isAccepted = orderClient.acceptOrder(orderTrack, courierId);

        assertTrue(isAccepted);
    }

    @Test
    @DisplayName("Check possibility accepted order")
    @Description("Basic test for accepted order by courier without courier id")
    public void acceptOrderWithoutCourierIdTest() {
        Order order = new Order("Бобба", "Фет",
                "Галактическая империя, 1", "Сокольники", "+71236547456",
                7, "2022-11-28", "джедаи будут повержены");
        orderTrack = orderClient.orderCreate(order);

        String message = orderClient.failedAcceptOrderWithoutCourierId(orderTrack);

        assertEquals("Недостаточно данных для поиска", message);
    }

    @Test
    @DisplayName("Check possibility accepted order")
    @Description("Basic test for accepted order by courier with incorrect courier id")
    public void acceptOrderWithIncorrectCourierIdTest() {
        Order order = new Order("Бобба", "Фет",
                "Галактическая империя, 1", "Сокольники", "+71236547456",
                7, "2022-05-01", "джедаи будут повержены");
        orderTrack = orderClient.orderCreate(order);

        courierId = 0;

        String message = orderClient.failedAcceptOrderWithIncorrectId(orderTrack, courierId);

        assertEquals("Курьера с таким id не существует", message);
    }

    @Test
    @DisplayName("Check possibility accepted order")
    @Description("Basic test for accepted order by courier without order id")
    public void acceptOrderWithoutOrderIdTest() {
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));

        String message = orderClient.failedAcceptOrderWithoutOrderId(courierId);

        assertEquals("Недостаточно данных для поиска", message);
    }

    @Test
    @DisplayName("Check possibility accepted order")
    @Description("Basic test for accepted order by courier with incorrect order id")
    public void acceptOrderWithIncorrectOrderIdTest() {
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));

        orderTrack = 0;

        String message = orderClient.failedAcceptOrderWithIncorrectOrderId(orderTrack, courierId);

        assertEquals("Заказа с таким id не существует", message);
    }
}
