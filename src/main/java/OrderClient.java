import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {
    private static final String ORDER_PATH = "api/v1/orders/";

    @Step
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step
    public int getOrderId(ValidatableResponse response) {
        return response
                .extract()
                .path("track");
    }

    @Step
    public ValidatableResponse getOrderInfo(int orderId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH + "track?t=" + orderId)
                .then();
    }
}
