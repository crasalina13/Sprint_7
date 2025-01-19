import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class CourierParameterizedFailedLoginTest {

    private final Courier courier;
    private final String expectedErrorMessage;
    private final int expectedStatusCode;


    public CourierParameterizedFailedLoginTest(Courier courier, String expectedErrorMessage, int expectedStatusCode) {
        this.courier = courier;
        this.expectedErrorMessage = expectedErrorMessage;
        this.expectedStatusCode = expectedStatusCode;
    }


    @Parameterized.Parameters
    public static Object[][] getCourierInfo() {
        return new Object[][]{
                {Courier.getWithLoginOnly(), "Недостаточно данных для входа", 400},
                {Courier.getWithPasswordOnly(), "Недостаточно данных для входа", 400},
                {Courier.getRandom(), "Учетная запись не найдена", 404}
        };
    }


    @Test
    public void courierFailedLoginTest() {
        ValidatableResponse response = new CourierClient().login(CourierCredentials.from(courier));

        int statusCode = response.extract().statusCode();
        assertThat("Response incorrect status code", statusCode, equalTo(expectedStatusCode));

        String errorMessage = response.extract().path("message");
        assertThat("Response incorrect error message", errorMessage, equalTo(expectedErrorMessage));
    }
}
