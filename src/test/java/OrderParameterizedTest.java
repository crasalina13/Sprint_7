import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderParameterizedTest {

    private OrderClient orderClient;
    private int orderId;

    public final String[] color;

    public OrderParameterizedTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderInfo() {
        return new Object[][]{
                {new String[]{}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{"GREY"}}
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    public void orderSuccessCreatedFieldColorTest() {
        ValidatableResponse response = orderClient.create(Order.getOrderWithSetColor(color));

        int orderId = orderClient.getOrderId(response);

        assertTrue("Order is not created", orderId > 0);
    }
}
