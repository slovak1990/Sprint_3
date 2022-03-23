package feature2;

import couriers.Courier;
import couriers.CourierClient;
import couriers.CourierCredentials;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import logins.LoginClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest extends FeatureAllure2 {

    private CourierClient courierClient;
    private LoginClient loginClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        loginClient = new LoginClient();
    }

    @After
    public void teardown() { courierClient.delete(courierId);}

    @Test
    @DisplayName("Check validation courier")
    @Description("Basic test login courier is validated")
    public void isValidatedLoginTest() {
        Courier courier = Courier.getRandom();
        boolean isCreated = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));

        assertTrue(isCreated);
    }

    @Test
    @DisplayName("Check validation courier")
    @Description("Basic test for have id courier")
    public void validateLoginTest() {
        Courier courier = Courier.getRandom();
        courierClient.create(courier);

        courierId = courierClient.login(CourierCredentials.from(courier));

        assertNotEquals(0, courierId);
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
