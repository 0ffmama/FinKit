package ua.kpi.tzyapz.finkit.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;

@Tag("ui")
@DisplayName("UI: відкриття рахунку (F-60)")
class OpenAccountTest extends ParaBankUiBase {

    @Test
    @DisplayName("TC-018: новий рахунок SAVINGS — «Account Opened!» і номер рахунку")
    void openSavingsAccountShowsNewNumber() {
        newCustomer();
        String number = openAccount("SAVINGS");

        assertAll(
                () -> assertEquals("Account Opened!", driver.findElement(By.cssSelector("#openAccountResult h1.title")).getText()),
                () -> assertTrue(number.matches("\\d+"), number));
    }
}
