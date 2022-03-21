package couriers;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;


public class CourierGeneration {

    @Step("Create random courier")
    public static Courier getRandom() {
        List<String> loginPassResult = registerNewCourierAndReturnLoginPassword2();
        Courier courier = new Courier(loginPassResult.get(0), loginPassResult.get(1), loginPassResult.get(2));
        return courier;
    }



    /*
        метод регистрации нового курьера
        возвращает список из логина и пароля
        если регистрация не удалась, возвращает пустой список
        */
    public static ArrayList<String> registerNewCourierAndReturnLoginPassword(){

        // с помощью библиотеки RandomStringUtils генерируем логин
        // метод randomAlphabetic генерирует строку, состоящую только из букв, в качестве параметра передаём длину строки
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем пароль
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем имя курьера
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);

        // создаём список, чтобы метод мог его вернуть
        ArrayList<String> loginPass = new ArrayList<>();

        // собираем в строку тело запроса на регистрацию, подставляя в него логин, пароль и имя курьера
        String registerRequestBody = "{\"login\":\"" + courierLogin + "\","
                + "\"password\":\"" + courierPassword + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";

        // отправляем запрос на регистрацию курьера и сохраняем ответ в переменную response класса Response
        Response response =  given()
                .header("Content-type", "application/json")
                .and()
                .body(registerRequestBody)
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier");

        // если регистрация прошла успешно (код ответа 201), добавляем в список логин и пароль курьера
        if (response.statusCode() == 201) {
            loginPass.add(courierLogin);
            loginPass.add(courierPassword);
            loginPass.add(courierFirstName);
        }

        // возвращаем список
        return loginPass;
    }

    public static ArrayList<String> registerNewCourierAndReturnLoginPassword2() {
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);

        ArrayList<String> loginPass = new ArrayList<>();

        loginPass.add(courierLogin);
        loginPass.add(courierPassword);
        loginPass.add(courierFirstName);


        return loginPass;
    }


}