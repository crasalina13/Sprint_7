import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;

public class CourierTest {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }


    @Test
    public void courierDoubleCreatedReturnErrorTest() {
        courierClient.create(courier);
        ValidatableResponse response = courierClient.create(courier);
        String expectedErrorMessage = "Этот логин уже используется. Попробуйте другой.";

        response.assertThat().statusCode(SC_CONFLICT);
        response.body("message", equalTo(expectedErrorMessage));
    }

    @Test
    public void courierSuccessLoginTest() {
        courierClient.create(courier);
        ValidatableResponse response = courierClient.login(CourierCredentials.from(courier));

        response.assertThat().statusCode(SC_OK);
        response.body("id", greaterThan(0));
    }

    @Test
    public void courierDeleteTest() {
        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
        ValidatableResponse response = courierClient.delete(courierId);

        response.assertThat().statusCode(SC_OK);
        response.body("ok", is(true));
    }
}
