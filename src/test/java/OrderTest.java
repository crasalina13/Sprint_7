import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;


public class OrderTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    public void getInfoOrderTest() {
        int orderId = orderClient
                .create(Order.getOrderWithSetColor(new String[]{}))
                .extract()
                .path("track");

        ValidatableResponse response = orderClient.getOrderInfo(orderId);

        response.assertThat().statusCode(SC_OK);
        response.body("order", is(not(emptyOrNullString())));
    }
}