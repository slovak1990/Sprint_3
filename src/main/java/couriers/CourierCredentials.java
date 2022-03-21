package couriers;

import lombok.Data;

@Data // lombok плагин где сетеры и гетеры по умолч
public class CourierCredentials {
    private String login;
    private String password;

    public CourierCredentials(String login, String password){
        this.login = login;
        this.password = password;
    }

    public static CourierCredentials from(Courier courier) {
        return new CourierCredentials(courier.getLogin(), courier.getPassword());
    }
}
