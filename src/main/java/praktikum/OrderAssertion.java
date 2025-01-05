package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.*;

public class OrderAssertion {
    @Step("Создание заказа с авторизацией")
    public void assertCreationOrder(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Step("Создание заказа без авторизации")
    public void assertCreationOrderNoAuth(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("success", is(false));
    }

    @Step("Создание заказа с ингредиентами")
    public void assertCreationOrderWithIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Step("Создание заказа без ингредиентов")
    public void CreationOrderNoIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Создание заказа с неверным хешем ингредиентов")
    public void assertCreationOrderWithHashIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("message",equalTo("One or more ids provided are incorrect") );
    }

    @Step("Получение заказов авторизованного пользователя") //ok
    public void getOrderLoginUser(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Step("Получение заказов неавторизованного пользователя")
    public void getOrderNoLoginUser(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }
}
