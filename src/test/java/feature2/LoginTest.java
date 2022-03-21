package feature2;

import couriers.Courier;
import couriers.CourierClient;
import couriers.CourierCredentials;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import logins.LoginClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {

    private CourierClient courierClient;
    private LoginClient loginClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        loginClient = new LoginClient();
    }

    @Test
    @DisplayName("Check validation courier")
    @Description("Basic test for validation login courier")
    public void validateLoginTest() {
        Courier courier = Courier.getRandom();
        boolean isCreated = courierClient.create(courier);

        courierId = courierClient.login(CourierCredentials.from(courier));

        assertTrue(isCreated);
        assertNotEquals(0, courierId);

        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Check validation courier")
    @Description("Basic test for error validation courier without login")
    public void validateWithoutLoginTest() {
        CourierCredentials credentials =
            CourierCredentials.builder().login(null).password(RandomStringUtils.randomAlphabetic(10)).build();
        String message = loginClient.failedLoginValidate(credentials);

        assertEquals("Недостаточно данных для входа", message);
    }

    @Test
    @DisplayName("Check validation courier")
    @Description("Basic test for error validation courier without password")
    public void validateWithoutPasswordTest() {
        CourierCredentials credentials =
                CourierCredentials.builder().login(RandomStringUtils.randomAlphabetic(10)).password(null).build();
        String message = loginClient.failedLoginValidate(credentials);

        assertEquals("Недостаточно данных для входа", message);
    }

    @Test
    @DisplayName("Check validation courier")
    @Description("Basic test for error validation courier with incorrect login")
    public void validateWithWrongLogin() {
        CourierCredentials credentials =
                CourierCredentials.builder().login(RandomStringUtils.randomAlphabetic(10))
                                            .password(RandomStringUtils.randomAlphabetic(10))
                                            .build();
        String message = loginClient.wrongLoginValidate(credentials);

        assertEquals("Учетная запись не найдена", message);
    }
}
