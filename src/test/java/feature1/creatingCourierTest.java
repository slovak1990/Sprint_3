package feature1;

import couriers.Courier;
import couriers.CourierClient;
import couriers.CourierCredentials;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class creatingCourierTest {

    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    public void courier() {
        Courier courier = Courier.yandexGetRandom();
        boolean isCreated = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));

        assertTrue(isCreated);
        assertNotEquals(0, courierId);
        courierClient.delete(courierId);
    }

    @Test
    public void creatingCourierWithoutLogin() {
        Courier courier = Courier.builder()
                .firstName(RandomStringUtils.randomAlphabetic(10))
                .password(RandomStringUtils.randomAlphabetic(10))
                .build();

        String errorMessage = courierClient.createFailed(courier);
        assertEquals("Недостаточно данных для создания учетной записи", errorMessage);
    }

    @Test
    public void creatingCourierWithoutPassword() {
        Courier courier = Courier.builder()
                .firstName(RandomStringUtils.randomAlphabetic(10))
                .login(RandomStringUtils.randomAlphabetic(10))
                .build();

        String errorMessage = courierClient.createFailed(courier);
        assertEquals("Недостаточно данных для создания учетной записи", errorMessage);
    }

    @Test
    public void secondCourierNotBeCreated() {
        Courier courier = Courier.yandexGetRandom();
        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));
        String errorMessage = courierClient.createSecondCourier(courier);

        assertEquals("Этот логин уже используется. Попробуйте другой.", errorMessage);
        courierClient.delete(courierId);
    }
}
