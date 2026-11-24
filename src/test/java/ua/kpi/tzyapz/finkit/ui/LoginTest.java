package ua.kpi.tzyapz.finkit.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

@Tag("ui")
@DisplayName("UI: вхід клієнта (F-20)")
class LoginTest extends ParaBankUiBase {

    @Test
    @DisplayName("TC-005: коректний вхід відкриває «Огляд рахунків» із привітанням")
    void validLoginOpensAccountsOverview() {
        String user = newCustomer();
        logOut();
        logIn(user, PASS);

        wait.until(textToBePresentInElementLocated(TITLE, "Accounts Overview"));
        assertAll(
                () -> assertTrue(driver.getCurrentUrl().contains("overview.htm")),
                () -> assertTrue(driver.findElement(By.cssSelector("#leftPanel p.smallText")).getText().contains("Andrii Testenko")),
                () -> assertFalse(driver.findElements(LOGOUT).isEmpty()));
    }

    @Test
    @DisplayName("TC-006: неправильний пароль — помилка, кабінет не відкрито")
    void wrongPasswordShowsError() {
        String user = newCustomer();
        logOut();
        logIn(user, "Wrong#999");

        String error = wait.until(visibilityOfElementLocated(By.cssSelector("#rightPanel p.error"))).getText();
        assertAll(
                () -> assertTrue(error.contains("could not be verified"), error),
                () -> assertTrue(driver.findElements(LOGOUT).isEmpty()));
    }
}
