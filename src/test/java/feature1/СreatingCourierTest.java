package feature1;

import couriers.Courier;
import couriers.CourierClient;
import couriers.CourierCredentials;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class СreatingCourierTest extends FeatureAllure1{

    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void teardown() { courierClient.delete(courierId);}

    @Test
    @DisplayName("Creating courier")
    @Description("Basic test courier is created")
    public void isCreatingCourierTest() {
        Courier courier = Courier.getRandom();
        boolean isCreated = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));

        assertTrue(isCreated);
    }

    @Test
    @DisplayName("Creating courier")
    @Description("Basic test cheking Id courier")
    public void checkingCourierIdTest() {
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));

        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Creating courier")
    @Description("Basic test for create courier without login")
    public void creatingCourierWithoutLoginTest() {
        Courier courier = Courier.builder()
                .firstName(RandomStringUtils.randomAlphabetic(10))
                .password(RandomStringUtils.randomAlphabetic(10))
                .build();

        String message = courierClient.createFailed(courier);

        assertEquals("Недостаточно данных для создания учетной записи", message);
    }

    @Test
    @DisplayName("Creating courier")
    @Description("Basic test for create courier without password")
    public void creatingCourierWithoutPasswordTest() {
        Courier courier = Courier.builder()
                .firstName(RandomStringUtils.randomAlphabetic(10))
                .login(RandomStringUtils.randomAlphabetic(10))
                .build();

        String message = courierClient.createFailed(courier);

        assertEquals("Недостаточно данных для создания учетной записи", message);
    }

    @Test
    @DisplayName("Creating courier")
    @Description("Basic test for can't be created second courier")
    public void secondCourierNotBeCreatedTest() {
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));

        String message = courierClient.createSecondCourier(courier);

        assertEquals("Этот логин уже используется. Попробуйте другой.", message);
    }
}
