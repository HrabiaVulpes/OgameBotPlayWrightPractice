package org.vulpes;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.vulpes.game.GameDataSingleton;
import org.vulpes.pages.EntryPage;


public class Main {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
            Page page = browser.newPage();
            EntryPage entryPage = new EntryPage(page);
            entryPage.open()
                    .acceptCookies()
                    .clickOnLoginTab()
                    .fillEmail("hrabiavulpes@gmail.com")
                    .fillPassword("zaq1@WSXcde3")
                    .clickLoginButton()
                    .openLastPlayed()
                    .collectResourceData();

            System.out.println(GameDataSingleton.deuter);
        }
    }
}