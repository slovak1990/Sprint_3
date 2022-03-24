package logins;

import couriers.CourierCredentials;
import couriers.ScooterRestClient;

import static io.restassured.RestAssured.given;

public class LoginClient extends ScooterRestClient {

    private static final String COURIER_PATH = "api/v1/courier/";

    public String failedLoginValidate(CourierCredentials credentials) {
        return given().log().all()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

    public String wrongLoginValidate(CourierCredentials credentials) {
        return given().log().all()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");
    }
}
