package order;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.*;

import java.util.List;

public class OrderTest {
    private String accessToken;
    private BaseUrl baseUrl;
    private final OrderAssertion orderAssertion = new OrderAssertion();

    protected final UserRandom random = new UserRandom();

    @Before
    @Step("Предусловия для логина")
    public void setUp() {
        baseUrl = new BaseUrl();
        final UserCreate randomUniqueUser = random.random();
        final UserAssertion userAssertion = new UserAssertion();
        ValidatableResponse create = baseUrl.register(randomUniqueUser);
        accessToken = userAssertion.assertCreationSusses(create);
    }

    @Test
    @DisplayName("Проверка создания заказа зарегестрированным пользователем")
    @Description("Можно войти с существующими данными")
    public void checkCreateOrderInLoginUser() {
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
        OrderCreate orderCreate = new OrderCreate(ingredients);
        ValidatableResponse response = baseUrl.createOrders(accessToken, orderCreate);
        orderAssertion.assertCreationOrder(response);
    }

    @Test
    @DisplayName("Проверка создания заказа незарегестрированным пользователем")
    @Description("Нельзя войти с существующими данными")
    public void checkCreateOrderNoLoginUser() {
        List<String> ingredients = List.of(
                "60d3463f7034a000269f45e7",
                "60d3463f7034a000269f45e9",
                "60d3463f7034a000269f45e8",
                "60d3463f7034a000269f45ea");
        OrderCreate orderCreate = new OrderCreate(ingredients);
        ValidatableResponse response = baseUrl.createOrders("123", orderCreate);
        orderAssertion.assertCreationOrderNoAuth(response);
    }

    @Test
    @DisplayName("Проверка создания заказа c неверным хешами ингредиентов")
    @Description("Сообщение об ошибке хэша")
    public void checkCreateOrderHashIngr() {
        List<String> ingredients = List.of(
                "60d3463f7034a000269f45e7",
                "60d3463f7034a000269f45e9",
                "60d3463f7034a000269f45e8",
                "60d3463f7034a000269f45ea");
        OrderCreate orderCreate = new OrderCreate(ingredients);
        ValidatableResponse response = baseUrl.createOrders(accessToken, orderCreate);
        orderAssertion.assertCreationOrderWithHashIngredients(response);
    }

    @Test
    @DisplayName("Проверка получения ошибки при попытки создания заказа без ингредиентов")
    @Description("Сообщение об ошибке")
    public void checkCreateOrderNoIngredients() {
        OrderCreate orderCreate = new OrderCreate(null);
        ValidatableResponse response = baseUrl.createOrders(accessToken, orderCreate);
        orderAssertion.CreationOrderNoIngredients(response);
    }

    @Test
    @DisplayName("Проверка создания заказа c ингредиентами")
    @Description("Успешное создание заказа")
    public void checkCreateOrderWithIngredients() {
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
        OrderCreate orderCreate = new OrderCreate(ingredients);
        ValidatableResponse response = baseUrl.createOrders(accessToken, orderCreate);
        orderAssertion.assertCreationOrderWithIngredients(response);
    }

    @After
    @Step("Удаление пользователя")
    public void deleteUser() {
        baseUrl.delete(accessToken);
    }
}