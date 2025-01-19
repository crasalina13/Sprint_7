import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

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

        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");

        assertThat("Status code is incorrect", statusCode, equalTo(400));
        assertThat("Response incorrect error message", errorMessage, equalTo("Недостаточно данных для создания учетной записи"));
    }
}
