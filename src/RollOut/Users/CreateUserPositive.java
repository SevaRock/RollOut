package RollOut.Users;

import RollOut.RollOutWeb;
import RollOut.RandomStr;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;

import static RollOut.RollOutConstants.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Проверка создания пользователя
 */

public class CreateUserPositive extends RollOutWeb {

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        //Открытие
        driver.get(URL_NSMS_SITE);
        wait.until(titleIs(TITLE_APP));
        driver.get(URL_NSMS_USERS);
    }

    @Test
    public void newUser() throws IOException, InterruptedException {
        //Проверка, что поля в карточке пользователя пустые по умолчанию
        driver.findElement(By.cssSelector("a.toolbar_button")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span.header_title")));
        Thread.sleep(1000);
        List<WebElement> elements = driver.findElements(By.cssSelector("div.host_input input"));
        Assert.assertEquals(elements.get(0).getAttribute("text"), null);
        Assert.assertEquals(elements.get(1).getAttribute("text"), null);
        Assert.assertEquals(elements.get(2).getAttribute("text"), null);
        Assert.assertEquals(driver.findElement(By.cssSelector("textarea")).getAttribute("text"), null);
        System.out.println("Элементы пустые");

        //Проверка имени позитивные сценарии
        createUser("1", "gmail@gmail.com", "+792423154131"); // 1 символ в имени
        createUser("a", "gmail@gmail.com", "+792423154131");// 1 буква в имени
        createUser(RandomStr.getStr(128), "gmail@gmail.com", "+792423154131"); //128 символов
        //createUser("a", "gmail@gmail.com", "+792423154131"); // имена не уникальны, баг

        //Проверка email позитивные сценарии
        createUser("User" + count, "z@1", "+7"); // 1 симв
        createUser("User" + count, RandomStr.getStr(63) + "@" + "g", "+7"); //63 и 1 симв
        createUser("User" + count, "A" + "@" + RandomStr.getStrD(252), "+7"); // 1 и 252
        createUser("User" + count, "3!#$%&'*+-/=?^_`{|}@d", "+7"); // спецсимволы в локальной части
        createUser("User" + count, (RandomStr.getStr(60) + "@" + RandomStr.getStrD(193)), "+7"); // 254 cимв

        //Проверка телефона позитивные сценарии, на текущей момент для поля нет ограничений
        createUser("User" + count, "z@1", "123"); // от трех симв
        createUser("User" + count, "z@1", "+000"); // + вначале
        createUser("User" + count, "z@1", "987654321099999"); // 15 символов
        createUser("User" + count, "z@1", "+987654321099999"); // 15 символов и +
        createUser("User" + count, "gmail@gmail.com", "   +7123456789 "); //15 симв с пробелами

        //Проверка Описание позитивные сценарии
        createUser("User" + count, "z@1", "+7", "."); // 1 симв
        createUser("User" + count, "z@1", "+7", "!#$%&'*+-/=?^_`{|}"); // спецсимволы
        createUser("User" + count, "z@1", "+7", RandomStr.getStr(128)); // 128 симв
    }

    @After
    public void tearDown() {
        //Очистка списка
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[text()='a']")));
            driver.findElement(By.cssSelector("tbody tr:first-child .checkbox")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Удалить пользователей']")));
            driver.findElement(By.cssSelector("a.toolbar_button:nth-child(1)")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.actions_button")));
            driver.findElement(By.cssSelector("button.actions_button")).click();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Ошибка очистки списка");
        } finally {
            //Выгрузка браузера
            driver.quit();
            driver = null;
        }
    }
}








