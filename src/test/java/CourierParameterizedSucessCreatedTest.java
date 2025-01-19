import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
    public void courierFailedCreatedTest() {
        ValidatableResponse response = courierClient.create(courier);


        int statusCode = response.extract().statusCode();
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
        Boolean isCourierCreated = response.extract().path("ok");

        assertThat("Status code is incorrect", statusCode, equalTo(201));
        assertTrue("Courier ID is incorrect", courierId > 0);
        assertTrue("Courier is not created", isCourierCreated);
    }
}
