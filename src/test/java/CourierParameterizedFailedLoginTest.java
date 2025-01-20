import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.CoreMatchers.equalTo;

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
                {Courier.getWithLoginOnly(), "Недостаточно данных для входа", SC_BAD_REQUEST},
                {Courier.getWithPasswordOnly(), "Недостаточно данных для входа", SC_BAD_REQUEST},
                {Courier.getRandom(), "Учетная запись не найдена", SC_NOT_FOUND}
        };
    }

    @Test
    public void courierFailedLoginTest() {
        ValidatableResponse response = new CourierClient().login(CourierCredentials.from(courier));

        response.assertThat().statusCode(expectedStatusCode);
        response.body("message", equalTo(expectedErrorMessage));
    }
}
