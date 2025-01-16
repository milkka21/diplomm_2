package praktikum;

import org.apache.commons.lang3.RandomStringUtils;

public class UserRandom {
    public UserCreate random() {
        return new UserCreate(RandomStringUtils.randomAlphanumeric(10), RandomStringUtils.randomAlphanumeric(6), RandomStringUtils.randomAlphanumeric(8)+ "@mail.ru");
    }
}
