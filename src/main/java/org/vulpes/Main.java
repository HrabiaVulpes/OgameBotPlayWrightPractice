package org.vulpes;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.vulpes.game.GameDataSingleton;
import org.vulpes.pages.EntryPage;
import org.vulpes.pages.OverviewPage;
import org.vulpes.pages.ResourcesPage;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            try (Playwright playwright = Playwright.create()) {
                Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100));
                Page page = browser.newPage();
                EntryPage entryPage = new EntryPage(page);
                OverviewPage resp = entryPage.open()
                        .acceptCookies()
                        .clickOnLoginTab()
                        .fillEmail("hrabiavulpes@gmail.com")
                        .fillPassword("zaq1@WSXcde3")
                        .clickLoginButton()
                        .openLastPlayed()
                        .collectResourceData()
                        .openResources()
                        .collectResourceBuildingLevels()
                        .openResearch()
                        .collectResearchLevels()
                        .openFacilities()
                        .collectFacilitiesBuildingLevels()
                        .openDefences()
                        .collectDefencesCounts()
                        .openFleet()
                        .collectFleetNumbers()
                        .sendAllExpeditions()
                        .openResearch()
                        .collectResourceData()
                        .openOverview();

                System.out.println("Builds? " + resp.isConstructing());
                System.out.println("BuildsLF? " + resp.isConstructingLifeforms());
                System.out.println("Researches? " + resp.isResearching());
                System.out.println("ResearchesLF? " + resp.isResearchingLifeforms());
                System.out.println("Produces? " + resp.isProducing());
                System.out.println("Resources are: " + GameDataSingleton.metal + " " + GameDataSingleton.crystal + " " + GameDataSingleton.deuter);
            }

            Thread.sleep(1000 * 60 * 15);
        }
    }
}