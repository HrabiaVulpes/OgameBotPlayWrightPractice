package org.vulpes.pages;

import com.microsoft.playwright.Page;
import org.vulpes.game.GameDataSingleton;

public class FleetPage extends GamePage {
    public FleetPage(Page page) {
        super(page);
    }

    public FleetPage collectFleetNumbers() {
        GameDataSingleton.shipCounts.clear();
        GameDataSingleton.shipCounts.put("fighterLight", Integer.parseInt(page.locator("span.fighterLight > span.amount").first().getAttribute("data-value")));
        GameDataSingleton.shipCounts.put("fighterHeavy", Integer.parseInt(page.locator("span.fighterHeavy > span.amount").first().getAttribute("data-value")));
        GameDataSingleton.shipCounts.put("cruiser", Integer.parseInt(page.locator("span.cruiser > span.amount").first().getAttribute("data-value")));
        GameDataSingleton.shipCounts.put("battleship", Integer.parseInt(page.locator("span.battleship > span.amount").first().getAttribute("data-value")));
        GameDataSingleton.shipCounts.put("interceptor", Integer.parseInt(page.locator("span.interceptor > span.amount").first().getAttribute("data-value")));
        GameDataSingleton.shipCounts.put("bomber", Integer.parseInt(page.locator("span.bomber > span.amount").first().getAttribute("data-value")));
        GameDataSingleton.shipCounts.put("destroyer", Integer.parseInt(page.locator("span.destroyer > span.amount").first().getAttribute("data-value")));
        GameDataSingleton.shipCounts.put("deathstar", Integer.parseInt(page.locator("span.deathstar > span.amount").first().getAttribute("data-value")));
        GameDataSingleton.shipCounts.put("reaper", Integer.parseInt(page.locator("span.reaper > span.amount").first().getAttribute("data-value")));
        GameDataSingleton.shipCounts.put("explorer", Integer.parseInt(page.locator("span.explorer > span.amount").first().getAttribute("data-value")));

        GameDataSingleton.shipCounts.put("transporterSmall", Integer.parseInt(page.locator("span.transporterSmall > span.amount").first().getAttribute("data-value")));
        GameDataSingleton.shipCounts.put("transporterLarge", Integer.parseInt(page.locator("span.transporterLarge > span.amount").first().getAttribute("data-value")));
        GameDataSingleton.shipCounts.put("colonyShip", Integer.parseInt(page.locator("span.colonyShip > span.amount").first().getAttribute("data-value")));
        GameDataSingleton.shipCounts.put("recycler", Integer.parseInt(page.locator("span.recycler > span.amount").first().getAttribute("data-value")));
        GameDataSingleton.shipCounts.put("espionageProbe", Integer.parseInt(page.locator("span.espionageProbe > span.amount").first().getAttribute("data-value")));

        GameDataSingleton.fleetsActive = Integer.parseInt(page.locator("div.fleetStatus > div.fleft > div.fleft > span.tooltip").first().textContent().split(":")[1].split("/")[0].trim());
        GameDataSingleton.maxFleetsActive = Integer.parseInt(page.locator("div.fleetStatus > div.fleft > div.fleft > span.tooltip").first().textContent().split(":")[1].split("/")[1].trim());

        GameDataSingleton.expeditionsActive = Integer.parseInt(page.locator("div.fleetStatus > div.fleft > div.fleft > span.tooltip").last().textContent().split(":")[1].split("/")[0].trim());
        GameDataSingleton.maxExpeditionsActive = Integer.parseInt(page.locator("div.fleetStatus > div.fleft > div.fleft > span.tooltip").last().textContent().split(":")[1].split("/")[1].trim());

        return this;
    }

    public FleetPage selectShips(String name, Integer count) {
        page.locator("li." + name + " > input").fill(count.toString());
        return this;
    }

    public FleetSendPage sendShips() {
        page.locator("a.continue").click();
        return new FleetSendPage(page);
    }

    public FleetPage sendExpedition() {
        collectFleetNumbers();
        int moreExpeditions = GameDataSingleton.maxExpeditionsActive - GameDataSingleton.expeditionsActive;
        if (moreExpeditions > 0) {
            if (GameDataSingleton.shipCounts.get("espionageProbe") > 0) selectShips("espionageProbe", 1);
            if (GameDataSingleton.shipCounts.get("explorer") > 0) selectShips("explorer", 1);
            if (GameDataSingleton.shipCounts.get("transporterSmall") > 0)
                selectShips("transporterSmall", GameDataSingleton.shipCounts.get("transporterSmall") / moreExpeditions);
            if (GameDataSingleton.shipCounts.get("transporterLarge") > 0)
                selectShips("transporterLarge", GameDataSingleton.shipCounts.get("transporterLarge") / moreExpeditions);

            if (GameDataSingleton.shipCounts.get("destroyer") > 0) selectShips("destroyer", 1);
            else if (GameDataSingleton.shipCounts.get("bomber") > 0) selectShips("bomber", 1);
            else if (GameDataSingleton.shipCounts.get("interceptor") > 0) selectShips("interceptor", 1);
            else if (GameDataSingleton.shipCounts.get("battleship") > 0) selectShips("battleship", 1);
            else if (GameDataSingleton.shipCounts.get("cruiser") > 0) selectShips("cruiser", 1);
            else if (GameDataSingleton.shipCounts.get("fighterHeavy") > 0) selectShips("fighterHeavy", 1);
            else if (GameDataSingleton.shipCounts.get("fighterLight") > 0) selectShips("fighterLight", 1);

            FleetSendPage fsp = sendShips();
            fsp.useCoordinates(2, 425, 16)
                    .pickMission("Ekspedycja")
                    .send();
        }
        return this;
    }

    public FleetPage sendAllExpeditions() {
        for (int i = GameDataSingleton.expeditionsActive; i < GameDataSingleton.maxExpeditionsActive; i++)
            sendExpedition();
        return this;
    }
}
