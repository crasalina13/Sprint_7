import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class OrderTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    public void getInfoOrderTest() {
        int orderId = orderClient.getOrderId(orderClient.create(Order.getOrderWithSetColor(new String[]{})));
        ValidatableResponse response = orderClient.getOrderInfo(orderId);

        int statusCode = response.extract().statusCode();
        HashMap<String, String> orderInfo = response.extract().path("order");

        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertFalse("Order is not found", orderInfo.isEmpty());
    }
}