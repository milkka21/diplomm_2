package user;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.BaseUrl;
import praktikum.UserAssertion;
import praktikum.UserCreate;
import praktikum.UserRandom;

public class UserUpdateTest {
    private String accessToken;
    private BaseUrl baseUrl;
    private UserCreate randomUniqueUser;
    private UserAssertion userAssertion;

    protected final UserRandom random = new UserRandom();

    @Before
    @Step("Предусловия для изменения данных")
    public void setUp() {
        baseUrl = new BaseUrl();
        randomUniqueUser = random.random();
        userAssertion = new UserAssertion();
        ValidatableResponse create = baseUrl.register(randomUniqueUser);
        accessToken = userAssertion.assertCreationSusses(create);
    }

    @Test
    @DisplayName("Изменение данных авторизированного пользователя")
    @Description("изменение email зарегестрированным пользователем")
    public void checkUpdateUserEmail() {
        randomUniqueUser.setEmail(randomUniqueUser.getEmail() + "qwe");
        ValidatableResponse response = baseUrl.update(accessToken, randomUniqueUser);
        userAssertion.updateUserSusses(response);
    }

    @Test
    @DisplayName("Изменение данных авторизированного пользователя")
    @Description("изменение пароля зарегестрированным пользователем")
    public void checkUpdateUserPassword() {
        randomUniqueUser.setPassword(randomUniqueUser.getPassword() + "qwe");
        ValidatableResponse response = baseUrl.update(accessToken, randomUniqueUser);
        userAssertion.updateUserSusses(response);
    }

    @Test
    @DisplayName("Изменение данных авторизированного пользователя")
    @Description("изменение имени зарегестрированным пользователем")
    public void checkUpdateUserName() {
        randomUniqueUser.setName(randomUniqueUser.getName() + "qwe");
        ValidatableResponse response = baseUrl.update(accessToken, randomUniqueUser);
        userAssertion.updateUserSusses(response);
    }

    @Test
    @DisplayName("Изменение данных неавторизированного пользователя")
    @Description("Проверка возможности изменения имени незарегестрированным пользователем")
    public void checkUpdateUserNameNoLogin() {
        randomUniqueUser.setName(randomUniqueUser.getName() + "qwe");
        ValidatableResponse response = baseUrl.update("null", randomUniqueUser);
        userAssertion.updateUserFailed(response);
    }

    @Test
    @DisplayName("Изменение данных неавторизированного пользователя")
    @Description("Проверка возможности изменения пароля незарегестрированным пользователем")
    public void checkUpdateUserPasswordNoLogin() {
        randomUniqueUser.setPassword(randomUniqueUser.getPassword() + "qwe");
        ValidatableResponse response = baseUrl.update("null", randomUniqueUser);
        userAssertion.updateUserFailed(response);
    }

    @Test
    @DisplayName("Изменение данных неавторизированного пользователя")
    @Description("Проверка возможности изменения email незарегестрированным пользователем")
    public void checkUpdateUserEmailNoLogin() {
        randomUniqueUser.setEmail(randomUniqueUser.getEmail() + "qwe");
        ValidatableResponse response = baseUrl.update("null", randomUniqueUser);
        userAssertion.updateUserFailed(response);
    }

    @After
    @Step("Удаление пользователя")
    public void deleteUser() {
        baseUrl.delete(accessToken);
    }
}
