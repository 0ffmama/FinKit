package ua.kpi.tzyapz.finkit.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

/** Браузер на кожен тест + кроки, спільні для сценаріїв ParaBank. Запуск із вікном: -Dheaded=true */
abstract class ParaBankUiBase {

    static final String URL = "https://parabank.parasoft.com/parabank/";
    static final String PASS = "TestPass2026!";
    static final By TITLE = By.cssSelector("#rightPanel h1.title");
    static final By LOGOUT = By.cssSelector("a[href*='logout.htm']");

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    void openBrowser() {
        ChromeOptions o = new ChromeOptions().addArguments("--window-size=1280,900");
        if (!Boolean.getBoolean("headed")) o.addArguments("--headless=new");
        driver = new ChromeDriver(o);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterEach
    void closeBrowser() {
        if (driver != null) driver.quit();
    }

    void register(String user, String pass, String confirm) {
        driver.get(URL + "register.htm");
        wait.until(visibilityOfElementLocated(By.id("customer.firstName"))).sendKeys("Andrii");
        String[][] fields = {{"lastName", "Testenko"}, {"address.street", "Testova 10"}, {"address.city", "Dnipro"},
                {"address.state", "Dnipro"}, {"address.zipCode", "49000"}, {"phoneNumber", "380001234567"},
                {"ssn", "1234567999"}, {"username", user}, {"password", pass}};
        for (String[] f : fields) driver.findElement(By.id("customer." + f[0])).sendKeys(f[1]);
        driver.findElement(By.id("repeatedPassword")).sendKeys(confirm);
        driver.findElement(By.cssSelector("input[value='Register']")).click();
    }

    /** Новий клієнт із латинськими даними (кирилицю блокує BUG-15); після реєстрації — вже у кабінеті. */
    String newCustomer() {
        String user = "gd" + System.currentTimeMillis();
        register(user, PASS, PASS);
        wait.until(textToBePresentInElementLocated(TITLE, "Welcome"));
        return user;
    }

    void logOut() {
        wait.until(elementToBeClickable(LOGOUT)).click();
    }

    void logIn(String user, String pass) {
        wait.until(visibilityOfElementLocated(By.name("username"))).sendKeys(user);
        driver.findElement(By.name("password")).sendKeys(pass);
        driver.findElement(By.cssSelector("input[value='Log In']")).click();
    }

    String openAccount(String type) {
        driver.get(URL + "openaccount.htm");
        wait.until(d -> !new Select(d.findElement(By.id("fromAccountId"))).getOptions().isEmpty());
        new Select(driver.findElement(By.id("type"))).selectByVisibleText(type);
        driver.findElement(By.cssSelector("input[value='Open New Account']")).click();
        return wait.until(visibilityOfElementLocated(By.id("newAccountId"))).getText();
    }
}
