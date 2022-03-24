package couriers;

import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient {
    private static final String COURIER_PATH = "api/v1/courier/";

    @Step ("Login with credentials {credentials}")
    public int login(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

    @Step("Creating Login courier")
    public boolean create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");
    }

    @Step("Deleting courier {id}")
    public boolean delete(int id) {
        return given().log().all()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + id)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }

    @Step("Deleting courier without id")
    public String deleteWithoutId() {
        return given().log().all()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH)
                .then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

    @Step("Deleting courier with wrong id")
    public String deleteWithWrongId(int id) {
        return given().log().all()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + id)
                .then()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");
    }

    @Step("Registration incorrect courier")
    public String createFailed(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then().log().all()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

    @Step("Registration second courier")
    public String createSecondCourier(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then().log().all()
                .assertThat()
                .statusCode(409)
                .extract()
                .path("message");
    }
}
