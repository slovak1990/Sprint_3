package orders;

import couriers.ScooterRestClient;
import io.qameta.allure.Step;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderClient extends ScooterRestClient {
    private static final String COURIER_PATH = "/api/v1/orders";

    @Step("Creating order")
    public int orderCreate(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(COURIER_PATH)
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("track");
    }

    @Step("Getting order body")
    public Map<String, ?> getOrder(int track) {
        return given()
                .spec(getBaseSpec())
                .queryParam("t", track)
                .when()
                .get(COURIER_PATH + "/track")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("order");
    }

    @Step("Cheking all orders body")
    public List<String> checkOrders() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(COURIER_PATH)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("orders");
    }

    @Step("Getting order without track")
    public String getOrderWithoutTrackId() {
        return given().log().all()
                .spec(getBaseSpec())
                .when()
                .get(COURIER_PATH + "/track")
                .then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

    @Step("Getting order with incorrect track")
    public String getOrderWithIncorrectTrackId(int track) {
        return given().log().all()
                .spec(getBaseSpec())
                .queryParam("t", track)
                .when()
                .get(COURIER_PATH + "/track")
                .then()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");
    }

    @Step("Accept order by courier")
    public boolean acceptOrder(int id, int courierId){
        return given().log().all()
                .spec(getBaseSpec())
                .queryParam("courierId", courierId)
                .when()
                .put(COURIER_PATH + "/accept/" + id)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }

    @Step("Failed accept order without id courier")
    public String failedAcceptOrderWithoutCourierId(int id) {
        return given().log().all()
                .spec(getBaseSpec())
                .when()
                .put(COURIER_PATH + "/accept/" + id)
                .then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

    @Step("Failed accept order with incorrect id courier")
    public String failedAcceptOrderWithIncorrectId(int id, int courierId) {
        return given().log().all()
                .spec(getBaseSpec())
                .queryParam("courierId", courierId)
                .when()
                .put(COURIER_PATH + "/accept/" + id)
                .then()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");
    }

    @Step("Failed accept order without order id")
    public String failedAcceptOrderWithoutOrderId(int courierId) {
        return given().log().all()
                .spec(getBaseSpec())
                .queryParam("courierId", courierId)
                .when()
                .put(COURIER_PATH + "/accept/")
                .then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

    @Step("Failed accept order with incorrect order id")
    public String failedAcceptOrderWithIncorrectOrderId(int id, int courierId) {
        return given().log().all()
                .spec(getBaseSpec())
                .queryParam("courierId", courierId)
                .when()
                .put(COURIER_PATH + "/accept/" + id)
                .then()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");
    }
}
