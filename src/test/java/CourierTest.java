import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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

        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");
        String expectedErrorMessage = "Этот логин уже используется. Попробуйте другой.";

        assertThat("Error message is incorrect", errorMessage, equalTo(expectedErrorMessage));
        assertThat("Status code is incorrect", statusCode, equalTo(409));
    }

    @Test
    public void courierSuccessLoginTest() {
        courierClient.create(courier);
        ValidatableResponse response = courierClient.login(CourierCredentials.from(courier));

        int statusCode = response.extract().statusCode();
        courierId = response.extract().path("id");

        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertTrue("Courier ID is incorrect", courierId > 0);
    }

    @Test
    public void courierDeleteTest() {
        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
        ValidatableResponse response = courierClient.delete(courierId);

        boolean isCourierDeleted = response.extract().path("ok");
        int statusCode = response.extract().statusCode();

        assertTrue("Courier is not delete", isCourierDeleted);
        assertThat("Status code is incorrect", statusCode, equalTo(200));
    }
}
