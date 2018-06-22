package by.gurinovich.googletask.pageobject.yandex;

import by.gurinovich.googletask.pageobject.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class YandexMainPage extends AbstractPage {

    private final String YANDEX_URL = "https://yandex.ru/";

    @FindBy(xpath = "//input[@id='text']")
    private WebElement searchTextField;

    @FindBy(xpath = "//div[@class='search2__button']")
    private WebElement searchBtn;


    public YandexMainPage(WebDriver driver) {
        super(driver);
        driver.get(YANDEX_URL);
    }

    public void doSearch(String request) {
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(searchTextField));
        searchTextField.clear();
        searchTextField.sendKeys(request);
        searchBtn.click();
    }

    public void navigateToMain() {
        driver.get(YANDEX_URL);
    }
}
