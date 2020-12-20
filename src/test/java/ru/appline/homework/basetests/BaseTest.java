package ru.appline.homework.basetests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.appline.framework.managers.PagesManager;
import ru.appline.framework.managers.PropManager;

import static ru.appline.framework.managers.DriverManager.getDriver;
import static ru.appline.framework.managers.InitManager.initFramework;
import static ru.appline.framework.managers.InitManager.quitFramework;
import static ru.appline.framework.utils.PropConst.APP_URL;

public class BaseTest {

    protected PagesManager app = PagesManager.getManagerPages();


    @BeforeAll
    static void beforeAll() {
        initFramework();
    }

    @BeforeEach
    void beforeEach() {
        getDriver().get(PropManager.getPropManager().getProperty(APP_URL));
    }

    @AfterAll
    static void afterAll() {
        quitFramework();
    }
}
