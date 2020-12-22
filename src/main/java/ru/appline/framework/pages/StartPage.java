package ru.appline.framework.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class StartPage extends BasePage {

    @FindBy(xpath = "//div[@class='service']")
    private List<WebElement> services;

    @FindBy(xpath = "//h1")
    private WebElement nextPageTitle;

    @Step("Переходим на страницу '{menuName}'")
    public ContributionsPage selectMenu(String menuName) {
        for (WebElement service : services) {
            String serviceNameXPath = ".//div[@class='service__title-text']";
            WebElement serviceName = service.findElement(By.xpath(serviceNameXPath));
            if (serviceName.getText().equalsIgnoreCase(menuName)) {
                service = service.findElement(By.xpath(".//div[@class='service__title']"));
                elementToBeClickable(service).click();
                wait.until(ExpectedConditions.visibilityOf(nextPageTitle));
                assertEquals(menuName, nextPageTitle.getText(),
                        "Должна была открыться страница '" + menuName + "', но открылась страница '"
                                + nextPageTitle.getText() + "'");
                return app.getContributionsPage();
            }
        }
        fail("Меню '" + menuName + "' не найдено");
        return app.getContributionsPage();
    }
}