package ua.kpi.tzyapz.finkit.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@Tag("ui")
@DisplayName("UI: реєстрація (GUI-30)")
class RegistrationTest extends ParaBankUiBase {

    @Test
    @DisplayName("TC-003: різні пароль і підтвердження — реєстрацію відхилено")
    void passwordMismatchIsRejected() {
        register("gd" + System.currentTimeMillis(), PASS, "TestPass2027!");

        String error = wait.until(visibilityOfElementLocated(By.id("repeatedPassword.errors"))).getText();
        assertAll(
                () -> assertEquals("Passwords did not match.", error),
                () -> assertTrue(driver.findElements(LOGOUT).isEmpty()));
    }
}
