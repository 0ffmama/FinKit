package ua.kpi.tzyapz.finkit.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/** Заготовка зі стартового проєкту, у норматив UI-сценаріїв не входить. */
@Tag("ui")
@DisplayName("Smoke: головна сторінка ParaBank відкривається")
class SmokeUiTest extends ParaBankUiBase {

    @Test
    void homePageShowsLoginBlock() {
        driver.get(URL + "index.htm");
        wait.until(visibilityOfElementLocated(By.name("username")));
        assertTrue(driver.getTitle().contains("ParaBank"), driver.getTitle());
    }
}
