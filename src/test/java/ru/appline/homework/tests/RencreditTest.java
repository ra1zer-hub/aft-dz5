package ru.appline.homework.tests;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.appline.framework.utils.MyAllureListener;
import ru.appline.homework.basetests.BaseTest;

@ExtendWith(MyAllureListener.class)
public class RencreditTest extends BaseTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {"Рубли|300 000|6|50 000|Ежемесячная капитализация|9 132,17|6 месяцев|250 000|559 132,17",
            "Доллары США|500 000|12|30 000|Ежемесячная капитализация|1 003,59|12 месяцев|330 000|831 003,59"})
    public void depositTest(String currency, String deposit, String period, String replenish, String checkbox,
                            String expectedAccrued, String expectedPeriod, String expectedTotalAmount,
                            String expectedWithdrawalAmount) {
        app.getStartPage()
                .selectMenu("Вклады")
                .selectCurrency(currency)
                .fillField("Сумма вклада", deposit)
                .fillSelect(period)
                .fillField("Ежемесячное пополнение", replenish)
                .selectCheckboxes(checkbox)
                .verificationOfCalculations(expectedAccrued, expectedPeriod, expectedTotalAmount, expectedWithdrawalAmount);
    }
}
