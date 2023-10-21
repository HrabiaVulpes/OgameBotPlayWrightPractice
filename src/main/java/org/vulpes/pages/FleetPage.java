package org.vulpes.pages;

import com.microsoft.playwright.Page;
import org.vulpes.game.GameDataSingleton;

public class FleetPage extends GamePage{
    public FleetPage(Page page) {
        super(page);
    }

    public FleetPage collectFleetNumbers(){
        GameDataSingleton.shipCounts.put("fighterLight", Integer.parseInt(page.locator("span.fighterLight > span.amount > span").first().textContent()));
        GameDataSingleton.shipCounts.put("fighterHeavy", Integer.parseInt(page.locator("span.fighterHeavy > span.amount > span").first().textContent()));
        GameDataSingleton.shipCounts.put("cruiser", Integer.parseInt(page.locator("span.cruiser > span.amount > span").first().textContent()));
        GameDataSingleton.shipCounts.put("battleship", Integer.parseInt(page.locator("span.battleship > span.amount > span").first().textContent()));
        GameDataSingleton.shipCounts.put("interceptor", Integer.parseInt(page.locator("span.interceptor > span.amount > span").first().textContent()));
        GameDataSingleton.shipCounts.put("bomber", Integer.parseInt(page.locator("span.bomber > span.amount > span").first().textContent()));
        GameDataSingleton.shipCounts.put("destroyer", Integer.parseInt(page.locator("span.destroyer > span.amount > span").first().textContent()));
        GameDataSingleton.shipCounts.put("deathstar", Integer.parseInt(page.locator("span.deathstar > span.amount > span").first().textContent()));
        GameDataSingleton.shipCounts.put("reaper", Integer.parseInt(page.locator("span.reaper > span.amount > span").first().textContent()));
        GameDataSingleton.shipCounts.put("explorer", Integer.parseInt(page.locator("span.explorer > span.amount > span").first().textContent()));

        GameDataSingleton.shipCounts.put("transporterSmall", Integer.parseInt(page.locator("span.transporterSmall > span.amount > span").first().textContent()));
        GameDataSingleton.shipCounts.put("transporterLarge", Integer.parseInt(page.locator("span.transporterLarge > span.amount > span").first().textContent()));
        GameDataSingleton.shipCounts.put("colonyShip", Integer.parseInt(page.locator("span.colonyShip > span.amount > span").first().textContent()));
        GameDataSingleton.shipCounts.put("recycler", Integer.parseInt(page.locator("span.recycler > span.amount > span").first().textContent()));
        GameDataSingleton.shipCounts.put("espionageProbe", Integer.parseInt(page.locator("span.espionageProbe > span.amount > span").first().textContent()));

        GameDataSingleton.fleetsActive = Integer.parseInt(page.locator("div.fleetStatus > div.fleft > div.fleft > span.tooltip").first().textContent().split(":")[1].split("/")[0].trim());
        GameDataSingleton.maxFleetsActive = Integer.parseInt(page.locator("div.fleetStatus > div.fleft > div.fleft > span.tooltip").first().textContent().split(":")[1].split("/")[1].trim());

        GameDataSingleton.expeditionsActive = Integer.parseInt(page.locator("div.fleetStatus > div.fleft > div.fleft > span.tooltip").last().textContent().split(":")[1].split("/")[0].trim());
        GameDataSingleton.maxExpeditionsActive = Integer.parseInt(page.locator("div.fleetStatus > div.fleft > div.fleft > span.tooltip").last().textContent().split(":")[1].split("/")[1].trim());

        return this;
    }


}
