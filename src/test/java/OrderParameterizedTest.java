import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(Parameterized.class)
public class OrderParameterizedTest {

    private OrderClient orderClient;

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

        response.assertThat().statusCode(SC_CREATED);
        response.body("track", is(notNullValue()));
        response.body("track", greaterThan(0));
    }
}
