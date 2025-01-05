package order;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.*;

public class OrderUserTest {
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
    @DisplayName("Проверка списка заказа зарегестрированным пользователем")
    @Description("Можно получить список")
    public void checkCreateOrderInLoginUser() {
        ValidatableResponse response = baseUrl.getOrdersListUser(accessToken);
        orderAssertion.getOrderLoginUser(response);
    }

    @Test
    @DisplayName("Проверка списка заказа незарегестрированным пользователем")
    @Description("невозможно получить список")
    public void checkCreateOrderListNoLogin() {
        ValidatableResponse response = baseUrl.getOrdersListUser("123");
        orderAssertion.getOrderNoLoginUser(response);
    }

    @After
    @Step("Удаление пользователя")
    public void deleteUser() {
        baseUrl.delete(accessToken);
    }
}
