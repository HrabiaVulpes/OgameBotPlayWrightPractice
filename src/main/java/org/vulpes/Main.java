package org.vulpes;

import com.microsoft.playwright.*;
import org.vulpes.game.GameDataSingleton;
import org.vulpes.game.PlayerSettingsSingleton;
import org.vulpes.pages.EntryPage;
import org.vulpes.pages.OverviewPage;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            System.out.println("------WAKING UP----------------------------");
            try (Playwright playwright = Playwright.create()) {
                Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(100));
                Page page = browser.newPage();
                EntryPage entryPage = new EntryPage(page);
                OverviewPage current = entryPage.open()
                        .acceptCookies()
                        .clickOnLoginTab()
                        .fillEmail(PlayerSettingsSingleton.eMail)
                        .fillPassword(PlayerSettingsSingleton.pass)
                        .clickLoginButton()
                        .openLastPlayed()
                        .collectResourceData()
                        .openFleet()
                        .collectFleetNumbers()
                        .sendAllExpeditions()
                        .collectResourceData()
                        .openOverview();

                System.out.println("We have : " + current.getPlanetList().size() + " planets!");
                for (Locator planet : current.getPlanetList()) {
                    planet.click();

                    System.out.println("Builds? " + current.isConstructing());
                    System.out.println("Researches? " + current.isResearching());
                    System.out.println("Produces? " + current.isProducing());

                    if (!current.isConstructing())
                        current.openResources().constructNeeded().openOverview();
                    if (!current.isConstructing())
                        current.openFacilities().constructNeeded().openOverview();
                    if (!current.isResearching())
                        current.openResearch().upgradeLowestUsable().openOverview();
                    if (!current.isProducing())
                        current.openDefences().constructNeeded().openOverview();

                    System.out.println("Resources are: " + GameDataSingleton.metal + " " + GameDataSingleton.crystal + " " + GameDataSingleton.deuter);
                }
            }catch (Exception e){
                System.out.println("------FUCKING------------------------------");
                e.printStackTrace();
            }
            System.out.println("------SLEEPING-----------------------------");
            Thread.sleep(1000 * 60 * 5);
        }
    }
}