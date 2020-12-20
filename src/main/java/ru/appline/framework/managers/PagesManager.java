package ru.appline.framework.managers;


import ru.appline.framework.pages.ContributionsPage;
import ru.appline.framework.pages.StartPage;

public class PagesManager {

    private static PagesManager pagesManager;
    private StartPage startPage;
    private ContributionsPage contributionsPage;

    private PagesManager() {
    }

    public static PagesManager getManagerPages() {
        if (pagesManager == null) {
            pagesManager = new PagesManager();
        }
        return pagesManager;
    }

    public StartPage getStartPage() {
        if (startPage == null) {
            startPage = new StartPage();
        }
        return startPage;
    }

    public ContributionsPage getContributionsPage() {
        if (contributionsPage == null) {
            contributionsPage = new ContributionsPage();
        }
        return contributionsPage;
    }
}
