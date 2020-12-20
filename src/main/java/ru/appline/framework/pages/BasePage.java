package ru.appline.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.appline.framework.managers.PagesManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.appline.framework.managers.DriverManager.getDriver;

public class BasePage {

    protected WebDriverWait wait = new WebDriverWait(getDriver(), 15, 1000);
    protected JavascriptExecutor js = (JavascriptExecutor) getDriver();
    protected PagesManager app = PagesManager.getManagerPages();
    protected Select select;

    public BasePage() {
        PageFactory.initElements(getDriver(), this);
    }

    protected WebElement elementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected boolean isChecked(WebElement element) {
        element = element.findElement(By.xpath("./input"));
        return element.getAttribute("checked").equalsIgnoreCase("true");
    }

    protected void fillInputField(WebElement field, String value) {
        elementToBeClickable(field).click();
        field.sendKeys(value);
        assertEquals(value, field.getAttribute("value"),
                "Поле заполнено не правильно.");
    }

    protected void dropdownList(WebElement element, String value) {
        select = new Select(element);
        select.selectByValue(value);
        String selectedOption = select.getFirstSelectedOption().getAttribute("value");
        assertEquals(value, selectedOption, "В выпадающем списке было выбрано некорректное значение");
    }

    protected void updateResult(WebElement actualValue, String currentValue) {
        String currentTotalAmount = actualValue.getText();
        int count = 0;
        while (actualValue.getText().equals(currentTotalAmount) && count < 25) {
            sleep(200);
            count++;
        }
    }

    protected void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
