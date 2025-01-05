package user;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.*;

public class UserLoginTest {
    private BaseUrl baseUrl;
    private UserCreate randomUniqueUser;
    private UserAssertion userAssertion;
    private String accessToken;

    protected final UserRandom random = new UserRandom();

    @Before
    @Step("Предусловия для логина")
    public void setUp() {
        baseUrl = new BaseUrl();
        randomUniqueUser = random.random();
        userAssertion = new UserAssertion();
        ValidatableResponse create = baseUrl.register(randomUniqueUser);
        accessToken = userAssertion.assertCreationSusses(create);
    }

    @Test
    @DisplayName("Логин Пользователя существующими данными")
    @Description("Можно войти с существующими данными")
    public void UserCanSuccessfullyLogin() {
        UserLogin userLogin = UserLogin.from(randomUniqueUser);
        ValidatableResponse login = baseUrl.login(userLogin);
        userAssertion.assertLoginSuccess(login);
    }

    @Test
    @DisplayName("Логин Пользователя с неправильным паролем")
    @Description("Попытка входа с неправильным паролем. Проверка сообщения об ошибке")
    public void userLoginUnsuccessfullyWithoutPassword() {
        UserLogin userLogin = UserLogin.from(randomUniqueUser);
        UserCanSuccessfullyLogin();
        userLogin.setPassword(userLogin.getPassword()+ "uy");
        ValidatableResponse login = baseUrl.login(userLogin);
        userAssertion.assertLoginFailed(login);
    }

    @Test
    @DisplayName("Логин Пользователя с неправильным полем Логин")
    @Description("Попытка входа с неправильным полем Логин. Проверка сообщения об ошибке")
    public void userLoginUnsuccessfullyWithoutEmail() {
        UserLogin userLogin = UserLogin.from(randomUniqueUser);
        UserCanSuccessfullyLogin();
        userLogin.setEmail(userLogin.getEmail()+ "uywe");
        ValidatableResponse login = baseUrl.login(userLogin);
        userAssertion.assertLoginFailed(login);
    }

    @After
    @Step("Удаление пользователя")
    public void deleteUser() {
        baseUrl.delete(accessToken);
    }
}
