import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.is;

@RunWith(Parameterized.class)
public class CourierParameterizedSucessCreatedTest {

    private final Courier courier;
    private int courierId;
    private CourierClient courierClient;


    public CourierParameterizedSucessCreatedTest(Courier courier) {
        this.courier = courier;
    }

    @Parameterized.Parameters
    public static Object[][] getCourierInfo() {
        return new Object[][]{
                {Courier.getRandom()},
                {Courier.getWithLoginAndPasswordOnly()}
        };
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }


    @Test
    public void courierSuccessCreatedTest() {
        ValidatableResponse response = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");

        response.assertThat().statusCode(SC_CREATED);
        response.body("ok", is(true));
    }
}
