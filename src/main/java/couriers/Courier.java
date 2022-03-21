package couriers;

import io.qameta.allure.Allure;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@Builder
public class Courier {
    private String login;
    private String password;
    private String firstName;

    public Courier(String login, String password, String firstName){
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static Courier getRandom() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);

        Allure.addAttachment("Login", login);
        Allure.addAttachment("Password", password);
        Allure.addAttachment("Name", firstName);

        return new Courier(login, password, firstName);
    }
}
