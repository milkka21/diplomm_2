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

public class UserCreateTest {
    private BaseUrl baseUrl;
    private UserCreate randomUniqueUser;
    private UserAssertion userAssertion;
    private String accessToken;

    protected final UserRandom random = new UserRandom();

    @Before
    @Step("Предусловия для создания Пользователя")
    public void setUp() {
        baseUrl = new BaseUrl();
        randomUniqueUser = random.random();
        userAssertion = new UserAssertion();
        accessToken = null;
    }

    @Test
    @DisplayName("Создание нового Пользователя")
    @Description("Пользователя можно создать")
    public void userCanBeCreated() {
        ValidatableResponse create = baseUrl.register(randomUniqueUser);
        accessToken = userAssertion.assertCreationSusses(create);
    }

    @Test
    @DisplayName("Создание Пользователя без заполнения поля логин")
    @Description("Пользователя нельзя создать без логина. Проверяем статус код и сообщение об ошибке")
    public void userCantCreatedNoEmail() {
        randomUniqueUser.setEmail(null);
        ValidatableResponse create = baseUrl.register(randomUniqueUser);
        userAssertion.assertCreationUserNoRequiredField(create);
    }

    @Test
    @DisplayName("Создание Пользователя без заполнения поля пароль")
    @Description("Пользователя нельзя создать без пароля. Проверяем статус код и сообщение об ошибке")
    public void userCantCreatedNoPassword() {
        randomUniqueUser.setPassword(null);
        ValidatableResponse create = baseUrl.register(randomUniqueUser);
        userAssertion.assertCreationUserNoRequiredField(create);
    }

    @Test
    @DisplayName("Создание Пользователя без заполнения поля имени")
    @Description("Пользователя нельзя создать без имени. Проверяем статус код и сообщение об ошибке")
    public void userCantCreatedNoName() {
        randomUniqueUser.setName(null);
        ValidatableResponse create = baseUrl.register(randomUniqueUser);
        userAssertion.assertCreationUserNoRequiredField(create);
    }

    @Test
    @DisplayName("Создание Пользователя  который уже зарегистрирован")
    @Description("Пользователя нельзя создать")
    public void userCantCreated() {
        ValidatableResponse create = baseUrl.register(randomUniqueUser);
        userAssertion.assertCreationSusses(create);

        ValidatableResponse create2 = baseUrl.register(randomUniqueUser);
        userAssertion.assertCreationUserFailed(create2);
    }

    @After
    @Step("Удаление пользователя")
    public void deleteUser() {
        if (accessToken != null) {
            baseUrl.delete(accessToken);
        }
    }
}
