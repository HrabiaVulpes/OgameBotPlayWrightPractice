package org.vulpes.tribalwars;

import com.microsoft.playwright.*;

import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class Bot {
    static Queue<String> listOfBarbarians = new LinkedBlockingQueue<>();
    static int GOOD_PRICE_BUY = 1600;
    static int GOOD_PRICE_SELL = 700;
    static int SEND_CAVALRY = 4;
    static Integer waitTime;
    private static String worldNr;

    public static void checkMarket(Page page) throws InterruptedException {
        String url = "https://pl" + worldNr + ".plemiona.pl/game.php?village=41747&screen=market&mode=exchange";
        if (!page.url().contains("screen=market") || !page.url().contains("mode=exchange")) page.navigate(url);
        Thread.sleep(1000);
        notABot(page);

        int woodPrice = Integer.parseInt(page.locator("#premium_exchange_rate_wood").textContent().split("⇄")[0].trim());
        int stonePrice = Integer.parseInt(page.locator("#premium_exchange_rate_stone").textContent().split("⇄")[0].trim());
        int ironPrice = Integer.parseInt(page.locator("#premium_exchange_rate_iron").textContent().split("⇄")[0].trim());

        int woodAmount = Integer.parseInt(page.locator("#wood").textContent());
        int stoneAmount = Integer.parseInt(page.locator("#stone").textContent());
        int ironAmount = Integer.parseInt(page.locator("#iron").textContent());

        int premiumAmount = Integer.parseInt(page.locator("#premium_points").textContent());
        int merchants = Integer.parseInt(page.locator("#market_merchant_available_count").textContent());

//        //Buy Resources if good price
//        if (woodPrice > GOOD_PRICE_BUY && premiumAmount > 0 && merchants > woodPrice / 1000.0) {
//            page.locator("#premium_exchange_buy_wood").locator("input.premium-exchange-input").fill("1");
//            page.getByText("Kalkuluj najlepszą ofertę ").click();
//            page.getByText("Potwierdź").click();
//            System.out.println("Kupiłem drewno za " + woodPrice);
//            return;
//        }
//
//        if (stonePrice > GOOD_PRICE_BUY && premiumAmount > 0 && merchants > stonePrice / 1000.0) {
//            page.locator("#premium_exchange_buy_stone").locator("input.premium-exchange-input").fill("1");
//            page.getByText("Kalkuluj najlepszą ofertę ").click();
//            page.getByText("Potwierdź").click();
//            System.out.println("Kupiłem kamień za " + stonePrice);
//            return;
//        }
//
//        if (ironPrice > GOOD_PRICE_BUY && premiumAmount > 0 && merchants > ironPrice / 1000.0) {
//            page.locator("#premium_exchange_buy_iron").locator("input.premium-exchange-input").fill("1");
//            page.getByText("Kalkuluj najlepszą ofertę ").click();
//            page.getByText("Potwierdź").click();
//            System.out.println("Kupiłem żelazo za " + ironPrice);
//            return;
//        }

        //Sell resources if good price
        if (woodPrice < GOOD_PRICE_SELL && woodAmount > GOOD_PRICE_SELL + 100 && merchants > woodPrice / 1000.0) {
            page.locator("#premium_exchange_sell_wood").locator("input.premium-exchange-input").fill("1");
            page.getByText("Kalkuluj najlepszą ofertę ").click();
            page.getByText("Potwierdź").click();
            System.out.println("Sprzedałem drewno za " + woodPrice);
            return;
        }

        if (stonePrice < GOOD_PRICE_SELL && stoneAmount > GOOD_PRICE_SELL + 100 && merchants > stonePrice / 1000.0) {
            page.locator("#premium_exchange_sell_stone").locator("input.premium-exchange-input").fill("1");
            page.getByText("Kalkuluj najlepszą ofertę ").click();
            page.getByText("Potwierdź").click();
            System.out.println("Sprzedałem kamień za " + stonePrice);
            return;
        }

        if (ironPrice < GOOD_PRICE_SELL && ironAmount > GOOD_PRICE_SELL + 100 && merchants > ironPrice / 1000.0) {
            page.locator("#premium_exchange_sell_iron").locator("input.premium-exchange-input").fill("1");
            page.getByText("Kalkuluj najlepszą ofertę ").click();
            page.getByText("Potwierdź").click();
            System.out.println("Sprzedałem żelazo za " + ironPrice);
            return;
        }

        System.out.println("Ceny: " + woodPrice + " " + stonePrice + " " + ironPrice);
    }

    public static void openDaily(Page page) {
        if (page.locator("#daily_bonus_content").isVisible()) page.getByText("Otwórz").click();
    }

    public static void notABot(Page page) throws InterruptedException {
        if (page.getByText("Rozpocznij sprawdzanie ochrony botowej").isVisible()) {
            page.getByText("Rozpocznij sprawdzanie ochrony botowej").click();
            Thread.sleep(1000);
            page.frameLocator("div.captcha > iFrame").locator("#checkbox").click();
            System.out.println("Solved anti bot!");
        }
    }

    public static void sendRaids(Page page) throws InterruptedException {
        String url = "https://pl" + worldNr + ".plemiona.pl/game.php?village=41747&screen=place";
        if (!page.url().contains("screen=place")) page.navigate(url);
        Thread.sleep(1000);
        notABot(page);

        int lightCavalry = Integer.parseInt(page.locator("#units_entry_all_light").textContent().replace("(", "").replace(")", ""));
        int howMany = (int) Math.min(15 * Math.random() + 5, lightCavalry);
        for (int i = lightCavalry, j = 0; i > SEND_CAVALRY - 1 && j < howMany; i -= SEND_CAVALRY, j++) {
            sendRaid(page);
        }
        System.out.println("Done");
    }

    public static void gather(Page page) throws InterruptedException {
        String url = "https://pl" + worldNr + ".plemiona.pl/game.php?village=41747&screen=place&mode=scavenge";
        if (!page.url().contains("mode=scavenge")) page.navigate(url);
        Thread.sleep(1000);
        notABot(page);

        Locator lightCav = page.locator("a.units-entry-all[data-unit=\"light\"]");
        int lightCavalry = Integer.parseInt(lightCav.textContent().replace("(", "").replace(")", ""));
        int elevens = lightCavalry / 11;
        Locator lightCavInput = page.locator("input.unitsInput[name=\"light\"]");
        List<Locator> scavengeOptions = page.locator("div.scavenge-option").all();

        if (page.locator("a.free_send_button").all().size() >= 4) {
            scavengeOptions.get(3).locator("a.free_send_button").click();
            Thread.sleep(1000);

            lightCavInput.click();
            page.keyboard().type("" + (elevens * 2));
            Thread.sleep(1000);
            scavengeOptions.get(3).locator("a.free_send_button").click();
            Thread.sleep(1000);

            lightCavInput.click();
            page.keyboard().type("" + (elevens * 3));
            Thread.sleep(1000);
            scavengeOptions.get(2).locator("a.free_send_button").click();
            Thread.sleep(1000);

            lightCavInput.click();
            page.keyboard().type("" + (elevens * 6));
            Thread.sleep(1000);
            scavengeOptions.get(1).locator("a.free_send_button").click();
            Thread.sleep(1000);

            page.locator("a.units-entry-all[data-unit=\"axe\"]").click();
            page.locator("a.units-entry-all[data-unit=\"marcher\"]").click();
            Thread.sleep(1000);
            scavengeOptions.get(0).locator("a.free_send_button").click();
            Thread.sleep(1000);

            System.out.println("Gathering!");
        }
    }

    public static void gatherNonLight(Page page) throws InterruptedException {
        String url = "https://pl" + worldNr + ".plemiona.pl/game.php?village=41747&screen=place&mode=scavenge";
        if (!page.url().contains("mode=scavenge")) page.navigate(url);
        Thread.sleep(1000);
        notABot(page);

        List<Locator> scavengeOptions = page.locator("div.scavenge-option").all();

        if (page.locator("a.free_send_button").all().size() >= 4) {
            scavengeOptions.get(3).locator("a.free_send_button").click();
            Thread.sleep(1000);

            page.locator("a.units-entry-all[data-unit=\"axe\"]").click();
            page.locator("a.units-entry-all[data-unit=\"marcher\"]").click();
            Thread.sleep(1000);
            scavengeOptions.get(3).locator("a.free_send_button").click();
            Thread.sleep(1000);

            System.out.println("Gathering!");
        }
    }

    public static void sendRaid(Page page) throws InterruptedException {
        String url = "https://pl" + worldNr + ".plemiona.pl/game.php?village=41747&screen=place";
        if (!page.url().contains("screen=place")) page.navigate(url);
        Thread.sleep(1000);
        notABot(page);

        String target = listOfBarbarians.remove();
        page.locator("#unit_input_light").fill("" + SEND_CAVALRY);
        Thread.sleep(500);
        page.locator("input.target-input-field").fill(target);
        Thread.sleep(500);
        page.locator("input.btn-attack").click();
        Thread.sleep(500);

        if (page.locator("div.error_box > div.content").isVisible() && page.locator("div.error_box > div.content").textContent().contains("Grupa atakująca musi się składać z co najmniej")) {
            System.out.println("Village got civilized " + target);
            page.locator("img.village-delete").click();
        } else if (!page.locator("table.vis").getByText("Gracz:").isVisible()) {
            page.locator("input.troop_confirm_go ").click();
            System.out.println("Sent attack to: " + target);
            listOfBarbarians.add(target);
        } else {
            System.out.println("Village got civilized " + target);
        }
    }

    static void fillBarbs() {
        listOfBarbarians.add("307|578");
        listOfBarbarians.add("306|579");
        listOfBarbarians.add("305|581");
        listOfBarbarians.add("313|582");
        listOfBarbarians.add("320|583");
        listOfBarbarians.add("311|585");
        listOfBarbarians.add("323|586");
        listOfBarbarians.add("309|585");
        listOfBarbarians.add("321|585");
        listOfBarbarians.add("318|586");
        listOfBarbarians.add("318|586");
        listOfBarbarians.add("319|584");
        listOfBarbarians.add("313|584");
        listOfBarbarians.add("325|586");
        listOfBarbarians.add("323|588");
        listOfBarbarians.add("319|585");
        listOfBarbarians.add("318|588");
        listOfBarbarians.add("306|585");
        listOfBarbarians.add("320|580");
        listOfBarbarians.add("325|588");
        listOfBarbarians.add("317|578");
        listOfBarbarians.add("316|579");
        listOfBarbarians.add("317|584");
        listOfBarbarians.add("310|582");
        listOfBarbarians.add("311|578");
        listOfBarbarians.add("323|579");
        listOfBarbarians.add("325|581");
        listOfBarbarians.add("307|586");
        listOfBarbarians.add("324|579");
        listOfBarbarians.add("325|580");
        listOfBarbarians.add("316|583");
        listOfBarbarians.add("316|582");
        listOfBarbarians.add("310|584");
        listOfBarbarians.add("321|577");
        listOfBarbarians.add("311|578");
        listOfBarbarians.add("327|582");
        listOfBarbarians.add("327|584");
        listOfBarbarians.add("320|589");
        listOfBarbarians.add("308|583");
        listOfBarbarians.add("315|580");
        listOfBarbarians.add("327|580");
        listOfBarbarians.add("318|589");
        listOfBarbarians.add("321|576");
        listOfBarbarians.add("328|582");
        listOfBarbarians.add("327|587");
        listOfBarbarians.add("319|590");
        listOfBarbarians.add("323|590");
        listOfBarbarians.add("307|583");
        listOfBarbarians.add("307|584");
        listOfBarbarians.add("307|578");
        listOfBarbarians.add("329|584");
        listOfBarbarians.add("328|587");
        listOfBarbarians.add("322|591");
        listOfBarbarians.add("329|581");
        listOfBarbarians.add("306|579");
        listOfBarbarians.add("326|590");
        listOfBarbarians.add("321|574");
        listOfBarbarians.add("325|591");
        listOfBarbarians.add("330|584");
        listOfBarbarians.add("328|589");
        listOfBarbarians.add("316|575");
        listOfBarbarians.add("313|577");
        listOfBarbarians.add("327|591");
        listOfBarbarians.add("321|593");
        listOfBarbarians.add("322|593");
        listOfBarbarians.add("331|585");
        listOfBarbarians.add("318|593");
        listOfBarbarians.add("324|593");
        listOfBarbarians.add("329|576");
        listOfBarbarians.add("313|590");
        listOfBarbarians.add("329|590");
        listOfBarbarians.add("331|587");
        listOfBarbarians.add("321|572");
        listOfBarbarians.add("332|585");
        listOfBarbarians.add("329|575");
        listOfBarbarians.add("323|594");
        listOfBarbarians.add("324|572");
        listOfBarbarians.add("312|576");
        listOfBarbarians.add("330|576");
        listOfBarbarians.add("311|578");
        listOfBarbarians.add("330|590");
        listOfBarbarians.add("315|573");
        listOfBarbarians.add("331|577");
        listOfBarbarians.add("332|579");
        listOfBarbarians.add("332|587");
        listOfBarbarians.add("330|575");
        listOfBarbarians.add("311|576");
        listOfBarbarians.add("311|590");
        listOfBarbarians.add("323|595");
        listOfBarbarians.add("333|580");
        listOfBarbarians.add("315|594");
        listOfBarbarians.add("312|574");
        listOfBarbarians.add("311|575");
        listOfBarbarians.add("333|578");
        listOfBarbarians.add("319|570");
        listOfBarbarians.add("334|582");
        listOfBarbarians.add("328|594");
        listOfBarbarians.add("324|570");
        listOfBarbarians.add("311|574");
        listOfBarbarians.add("334|586");
        listOfBarbarians.add("318|596");
        listOfBarbarians.add("333|589");
        listOfBarbarians.add("317|570");
        listOfBarbarians.add("327|595");
        listOfBarbarians.add("331|592");
        listOfBarbarians.add("314|571");
        listOfBarbarians.add("321|569");
        listOfBarbarians.add("334|588");
        listOfBarbarians.add("323|569");
        listOfBarbarians.add("328|595");
        listOfBarbarians.add("335|582");
        listOfBarbarians.add("330|572");
        listOfBarbarians.add("321|597");
        listOfBarbarians.add("322|597");
        listOfBarbarians.add("327|570");
        listOfBarbarians.add("323|597");
        listOfBarbarians.add("310|592");
        listOfBarbarians.add("329|571");
        listOfBarbarians.add("312|594");
        listOfBarbarians.add("309|591");
        listOfBarbarians.add("322|568");
        listOfBarbarians.add("330|571");
        listOfBarbarians.add("331|594");
        listOfBarbarians.add("316|597");
        listOfBarbarians.add("329|570");
        listOfBarbarians.add("324|568");
        listOfBarbarians.add("314|569");
        listOfBarbarians.add("333|573");
        listOfBarbarians.add("330|570");
        listOfBarbarians.add("329|569");
        listOfBarbarians.add("306|577");
        listOfBarbarians.add("306|589");
        listOfBarbarians.add("333|594");
        listOfBarbarians.add("328|568");
        listOfBarbarians.add("331|596");
        listOfBarbarians.add("307|592");
        listOfBarbarians.add("312|597");
        listOfBarbarians.add("306|575");
        listOfBarbarians.add("333|595");
        listOfBarbarians.add("308|594");
        listOfBarbarians.add("310|596");
        listOfBarbarians.add("332|596");
        listOfBarbarians.add("335|593");
        listOfBarbarians.add("309|570");
        listOfBarbarians.add("309|596");
        listOfBarbarians.add("311|568");
        listOfBarbarians.add("332|597");
        listOfBarbarians.add("306|593");
        listOfBarbarians.add("333|569");
        listOfBarbarians.add("335|595");
        listOfBarbarians.add("333|597");
        listOfBarbarians.add("335|570");
        listOfBarbarians.add("307|596");
        listOfBarbarians.add("307|569");
        listOfBarbarians.add("307|597");

        listOfBarbarians = listOfBarbarians.stream().sorted(Comparator.comparing(a -> {
                    int x = Integer.parseInt(a.split("\\|")[0]);
                    int y = Integer.parseInt(a.split("\\|")[1]);
                    return (x - 321) * (x - 321) + (y - 583) * (y - 583);
                }
        )).collect(Collectors.toCollection(LinkedBlockingQueue::new));
    }

    static void process(String nr, boolean raids, boolean gather) throws InterruptedException {
        Bot.worldNr = nr;
        System.out.println("------------------------- START " + nr + " ------------------------");
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(300));
            Page page = browser.newPage();
            page.navigate("https://plemiona.pl");
            page.locator("#user").fill("Hrabia Vulpes");
            page.locator("#password").fill("fatum71113");
            page.locator("a.btn-login").click();
            page.getByText("Świat " + nr).click();
            Thread.sleep(1000L);

            openDaily(page);
            if (raids) sendRaids(page);
            if (gather) gather(page);
            checkMarket(page);
        } catch (Exception e) {
            e.printStackTrace();
            waitTime = 1;
        }
    }

    public static void dayWork() throws InterruptedException {
        fillBarbs();
        SEND_CAVALRY = 4;

        while (true) {
            waitTime = (int) (15 * Math.random());

            GOOD_PRICE_SELL = 700;
            process("195", false, true);
            GOOD_PRICE_SELL = 1300;
            process("193", false, false);

            System.out.println("Waiting for " + waitTime + " minutes");
            Thread.sleep(1000L * 60 * waitTime);
        }
    }

    public static void nightWork() throws InterruptedException {
        fillBarbs();
        SEND_CAVALRY = 4;

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(300));
            Page page = browser.newPage();
            page.navigate("https://plemiona.pl");
            page.locator("#user").fill("Hrabia Vulpes");
            page.locator("#password").fill("fatum71113");
            page.locator("a.btn-login").click();
            page.locator("span.world_button_active").last().click();
            Thread.sleep(1000L);

            while (true) {
                int waitTime = (int) (15 * Math.random());
                try {
                    openDaily(page);
                    sendRaids(page);
                    checkMarket(page);

                } catch (Exception e) {
                    e.printStackTrace();
                    waitTime = 1;
                }
                System.out.println("Waiting for " + waitTime + " minutes");
                Thread.sleep(1000L * 60 * waitTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void work() throws InterruptedException {
        dayWork();
    }
}
