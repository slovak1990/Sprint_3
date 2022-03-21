package feature1;

import couriers.Courier;
import couriers.CourierClient;
import couriers.CourierCredentials;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
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
    @DisplayName("Creating courier")
    @Description("Basic test for create courier")
    public void creatingCourierTest() {
        Courier courier = Courier.getRandom();
        boolean isCreated = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));

        assertTrue(isCreated);
        assertNotEquals(0, courierId);
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Creating courier")
    @Description("Basic test for create courier without login")
    public void creatingCourierWithoutLoginTest() {
        Courier courier = Courier.builder()
                .firstName(RandomStringUtils.randomAlphabetic(10))
                .password(RandomStringUtils.randomAlphabetic(10))
                .build();

        String errorMessage = courierClient.createFailed(courier);
        assertEquals("Недостаточно данных для создания учетной записи", errorMessage);
    }

    @Test
    @DisplayName("Creating courier")
    @Description("Basic test for create courier without password")
    public void creatingCourierWithoutPasswordTest() {
        Courier courier = Courier.builder()
                .firstName(RandomStringUtils.randomAlphabetic(10))
                .login(RandomStringUtils.randomAlphabetic(10))
                .build();

        String errorMessage = courierClient.createFailed(courier);
        assertEquals("Недостаточно данных для создания учетной записи", errorMessage);
    }

    @Test
    @DisplayName("Creating courier")
    @Description("Basic test for can't be created second courier")
    public void secondCourierNotBeCreatedTest() {
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));
        String errorMessage = courierClient.createSecondCourier(courier);

        assertEquals("Этот логин уже используется. Попробуйте другой.", errorMessage);
        courierClient.delete(courierId);
    }
}
