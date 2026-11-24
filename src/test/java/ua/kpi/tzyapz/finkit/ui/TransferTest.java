package ua.kpi.tzyapz.finkit.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated;

@Tag("ui")
@DisplayName("UI: переказ коштів (F-70)")
class TransferTest extends ParaBankUiBase {

    @Test
    @DisplayName("TC-020: переказ 10.00 між власними рахунками — «Transfer Complete!»")
    void transferBetweenOwnAccounts() {
        newCustomer();
        String to = openAccount("CHECKING");

        driver.get(URL + "transfer.htm");
        wait.until(d -> new Select(d.findElement(By.id("toAccountId"))).getOptions().size() >= 2);
        Select fromList = new Select(driver.findElement(By.id("fromAccountId")));
        String from = fromList.getOptions().stream().map(o -> o.getText()).filter(t -> !t.equals(to)).findFirst().orElseThrow();
        fromList.selectByVisibleText(from);
        new Select(driver.findElement(By.id("toAccountId"))).selectByVisibleText(to);
        driver.findElement(By.id("amount")).sendKeys("10.00");
        driver.findElement(By.cssSelector("input[value='Transfer']")).click();

        wait.until(textToBePresentInElementLocated(By.cssSelector("#showResult h1.title"), "Transfer Complete!"));
        assertAll(
                () -> assertEquals("$10.00", driver.findElement(By.id("amountResult")).getText()),
                () -> assertEquals(from, driver.findElement(By.id("fromAccountIdResult")).getText()),
                () -> assertEquals(to, driver.findElement(By.id("toAccountIdResult")).getText()));
    }
}
