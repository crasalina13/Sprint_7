import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class CourierParameterizedFailedCreatedTest {

    private final Courier courier;
    private CourierClient courierClient;


    public CourierParameterizedFailedCreatedTest(Courier courier) {
        this.courier = courier;
    }


    @Parameterized.Parameters
    public static Object[][] getCourierInfo() {
        return new Object[][]{
                {Courier.getWithLoginOnly()},
                {Courier.getWithPasswordOnly()}
        };
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }


    @Test
    public void courierFailedCreatedTest() {
        ValidatableResponse response = courierClient.create(courier);

        response.assertThat().statusCode(SC_BAD_REQUEST);
        response.body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
