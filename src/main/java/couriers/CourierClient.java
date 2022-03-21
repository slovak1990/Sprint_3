import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient {
    private static final String COURIER_PATH = "api/v1/courier/";

    @Step ("Login with credentials {credentials}")
    public ValidatableResponse login(CourierCredential credential) {
        return given()
                .spec(getBaseSpec())
                .body(credential)
                .when()
                .post(COURIER_PATH + "login")
                .then();
    }

    @Step("Creating Login courier")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Deleting Login courier")
    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(getBaseSpec())
                .body(courierId)
                .when()
                .delete(COURIER_PATH + courierId)
                .then();
    }


}
