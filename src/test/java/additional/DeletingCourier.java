package additional;

import couriers.Courier;
import couriers.CourierClient;
import couriers.CourierCredentials;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Epic("Creating new user role")
@Feature("Delete of courier")
public class DeletingCourier {

    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Check possibility deleting courier")
    @Description("Basic test for delete courier")
    public void deletingCourierTest() {
        Courier courier = Courier.getRandom();
        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));

        boolean isDeleted = courierClient.delete(courierId);

        assertTrue(isDeleted);
    }

    @Test
    @DisplayName("Check possibility deleting courier")
    @Description("Basic test for delete courier without courier id")
    public void deletingCourierWithoutIdTest() {
        String errorMessage = courierClient.deleteWithoutId();

        assertEquals("Недостаточно данных для удаления курьера", errorMessage);
    }

    @Test
    @DisplayName("Check possibility deleting courier")
    @Description("Basic test for delete courier with incorrect courier id")
    public void deletingCourierWithWrongIdTest() {
        courierId = 55555;
        String errorMessage = courierClient.deleteWithWrongId(courierId);

        assertEquals("Курьера с таким id нет.", errorMessage);
    }
}
