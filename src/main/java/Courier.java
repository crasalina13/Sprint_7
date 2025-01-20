import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Courier {
    private String login;
    private String password;
    private String firstName;

    public static Courier getRandom() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }

    public static Courier getWithLoginOnly() {
        return new Courier(RandomStringUtils.randomAlphabetic(10), null, null);
    }

    public static Courier getWithPasswordOnly() {
        return new Courier(null, RandomStringUtils.randomAlphabetic(10), null);
    }

    public static Courier getWithLoginAndPasswordOnly() {
        return new Courier(
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10),
                null
        );
    }
}
