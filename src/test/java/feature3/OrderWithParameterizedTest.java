package feature3;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import orders.Order;
import orders.OrderClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class OrderWithParameterizedTest extends FeatureTest3 {

    private OrderClient orderClient;
    private final String[] colors;

    public OrderWithParameterizedTest(String[] colors) {
        this.colors = colors;
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Parameterized.Parameters
    public static Object[][] possibleColors() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK, GREY"}},
                {new String[]{}}
        };
    }

    @Test
    @DisplayName("Check creating orders with colors")
    @Description("Parameterised test order with colors")
    public void testWithParameters () {
        Order order = new Order("Асока", "Тано", "Галактическая республика, 1",
                "Окружная", "+71236547898", 2, "2022-04-28", "джедаи предатели");
        order.setColor(colors);

        int actual = orderClient.orderCreate(order);

        assertNotEquals(0, actual);
    }
}



