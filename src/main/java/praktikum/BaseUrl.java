package praktikum;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class BaseUrl {
    protected static final String BASEURL = "https://stellarburgers.nomoreparties.site/api";
    protected static final String ORDERS = "/orders";
    protected static final String AUTHUSER = "/auth/user";
    protected static final String AUTHREGISTER = "/auth/register";
    protected static final String AUTHLOGIN = "/auth/login";
    protected static final String INGREDIENTS = "/ingredients";


    @Step("Удаление пользователя")
    public void delete (String token) {
        given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .header("Authorization", token)
                .when()
                .delete(AUTHUSER)
                .then();
    }

    @Step ("Создание пользователя")
    public ValidatableResponse register (UserCreate userCreate) {
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .body(userCreate)
                .when()
                .post(AUTHREGISTER)
                .then();
    }

    @Step ("Вход в систему")
    public ValidatableResponse login (UserLogin userLogin) {
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .body(userLogin)
                .when()
                .post(AUTHLOGIN)
                .then();
    }
    @Step("Изменение данных пользователя")
    public ValidatableResponse update(String token, UserCreate userCreate) {
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .header("Authorization", token)
                .body(userCreate)
                .when()
                .patch(AUTHUSER)
                .then()
                .log()
                .all();
    }

    @Step("Создание заказа")
    public ValidatableResponse createOrders(String token, OrderCreate orderCreate) {
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .header("Authorization", token)
                .body(orderCreate)
                .when()
                .post(ORDERS)
                .then()
                .log()
                .all();
    }

    @Step("Получение заказов конкретного пользователя")
    public ValidatableResponse getOrdersListUser(String token) {
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .header("Authorization", token)
                .when()
                .get(ORDERS)
                .then()
                .log()
                .all();
    }
    @Step("Получение данных об ингредиентах")
    public List<String> getIngredients() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .when()
                .get(INGREDIENTS)
                .then().log().all()
                .extract().jsonPath().getList("data._id");
    }
}
