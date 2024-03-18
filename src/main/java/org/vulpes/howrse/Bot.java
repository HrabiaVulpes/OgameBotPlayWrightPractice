package org.vulpes.howrse;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Bot {
    public static void work() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(1000));
            Page page = browser.newPage();
            page.navigate("https://www.howrse.pl/jeu/");
            page.getByText("Nie wyrażam zgody na stosowanie plików cookies").click();
            page.locator("#login").fill("hrabiaVulpes");
            page.locator("#password").fill("ToJestHasło22");
            page.locator("#authentificationSubmit").click();
            page.navigate("https://www.howrse.pl/elevage/chevaux/?elevage=all-horses");
            page.locator("a.horsename").all().get(1).click();

            HorseProcessor horseProcessor = new HorseProcessor(page);


            while (horseProcessor.goNextHorse()){
                try {
                    horseProcessor.standardRoutine();
                }catch (Exception e){
                    System.out.println("Problems z: " + horseProcessor.getName());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
