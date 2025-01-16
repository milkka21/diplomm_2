package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.*;

public class UserAssertion {
    @Step("Пользователь c незаполненным обязательным полем")
    public void assertCreationUserNoRequiredField(ValidatableResponse response) {
        response.assertThat()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Пользователь с существующим логином")
    public void assertCreationUserFailed (ValidatableResponse response) {
        response.assertThat()
                .statusCode(403)
                .body("message", equalTo("User already exists"));
    }

    @Step("Упешный запрос по созданию пользователя")
    public String assertCreationSusses(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(200)
                .body("success", is(true))
                .extract().path("accessToken");
    }

    @Step ("Успешная авторизация в систему")
    public void assertLoginSuccess (ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("accessToken", notNullValue());
    }

    @Step ("Авторизация с неверным логином и паролем")
    public void assertLoginFailed (ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Step("Успешный запрос изменения данных зарегестрированным пользователем")
    public void updateUserSusses(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Step("запрос изменения данных без авторизации")
    public void updateUserFailed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }
}
