package ru.appline.framework.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ContributionsPage extends BasePage {


    @FindBy(xpath = "//label[@class='calculator__currency-field']")
    List<WebElement> listCurrency;

    @FindBy(xpath = "//input[@name='amount']")
    WebElement depositAmount;

    @FindBy(id = "period")
    WebElement period;

    @FindBy(name = "replenish")
    WebElement monthlyReplenish;

    @FindBy(xpath = "//form//label[@class='calculator__check-block']")
    List<WebElement> additionalOptions;

    @FindBy(xpath = "//span[@class='js-calc-earned']")
    WebElement accrued;

    @FindBy(xpath = "//span[@class='js-calc-period']")
    List<WebElement> replenishForThePeriod;

    @FindBy(xpath = "//span[@class='js-calc-replenish']")
    WebElement totalAmountOfReplenishment;

    @FindBy(xpath = "//span[@class='js-calc-result']")
    WebElement withdrawalAmount;

    @Step("Выбираем валюту '{currencyName}'")
    public ContributionsPage selectCurrency(String currencyName) {
        for (WebElement currency : listCurrency) {
            if (currency.getText().equalsIgnoreCase(currencyName)) {
                elementToBeClickable(currency).click();
                if (!isChecked(currency)) {
                    fail("Не получается выбрать валюту '" + currencyName + "'");
                }
                return app.getContributionsPage();
            }
        }
        fail("Валюта '" + currencyName + "' не найдена");
        return app.getContributionsPage();
    }
    @Step("Заполняем поле '{nameField}' значением '{value}'")
    public ContributionsPage fillField(String nameField, String value) {
        WebElement element = null;
        switch (nameField) {
            case "Сумма вклада":
                element = depositAmount;
                break;
            case "Ежемесячное пополнение":
                element = monthlyReplenish;
                break;
            default:
                fail("Поле с наименованием '" + nameField + "' отсутствует на странице 'Вклады'");
        }
        fillInputField(element, value);
        return app.getContributionsPage();
    }
    @Step("Выбираем срок вклада в месяцах: {value}")
    public ContributionsPage fillSelect(String value) {
        dropdownList(period, value);
        return app.getContributionsPage();
    }
    @Step("Подключаем услугу '{checkboxName}'")
    public ContributionsPage selectCheckboxes(String checkboxName) {
        for (WebElement checkbox : additionalOptions) {
            WebElement checkboxTitle = checkbox.findElement(By.xpath("./span[contains(@class, 'text')]"));
            if (checkboxTitle.getAttribute("textContent").equalsIgnoreCase(checkboxName)) {
                String currentTotalAmount = totalAmountOfReplenishment.getText();
                String currentAccrued = accrued.getText();
                WebElement checkboxChek = checkbox.findElement(By.xpath(".//input"));
                if (!checkboxChek.isSelected()) {
                    elementToBeClickable(checkbox).click();
                }
                updateResult(totalAmountOfReplenishment, currentTotalAmount);
                updateResult(accrued, currentAccrued);
                return app.getContributionsPage();
            }
        }
        fail("Не найдена опция '" + checkboxName + "'");
        return app.getContributionsPage();
    }
    @Step("Проверяем расчеты по вкладу")
    public ContributionsPage verificationOfCalculations(String expectedAccrued,
                                                        String expectedPeriod,
                                                        String expectedTotalAmountOfReplenishment,
                                                        String expectedWithdrawalAmount) {
        assertEquals(expectedAccrued, accrued.getText(), "Ошибка в начислении процентов");
        for (WebElement period : replenishForThePeriod) {
            assertEquals(expectedPeriod, period.getText(), "Ошибка в расчете периода");
        }
        assertEquals(expectedTotalAmountOfReplenishment, totalAmountOfReplenishment.getText(),
                "Ошибка в общей сумме пополнения");
        assertEquals(expectedWithdrawalAmount, withdrawalAmount.getText(),
                "Ошибка в расчете суммы снятия");
        return app.getContributionsPage();
    }
}
