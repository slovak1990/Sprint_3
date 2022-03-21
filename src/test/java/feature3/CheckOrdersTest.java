package feature3;

import orders.OrderClient;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class CheckOrdersTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    public void checkBodyOrderTest() {
        List<String> response = orderClient.checkOrders();

        assertNotNull(response);
    }
}
