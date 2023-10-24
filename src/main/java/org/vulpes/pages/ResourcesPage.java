package org.vulpes.pages;

import com.microsoft.playwright.Page;
import org.vulpes.game.GameDataSingleton;

import java.util.List;
import java.util.stream.Collectors;

import static org.vulpes.game.GameDataSingleton.buildingLevels;

public class ResourcesPage extends GamePage {
    public ResourcesPage(Page page) {
        super(page);
    }

    public ResourcesPage collectResourceBuildingLevels() {
        buildingLevels.clear();
        buildingLevels.put("metalMine", Integer.parseInt(page.locator("span.metalMine > span.level > span").first().textContent()));
        buildingLevels.put("crystalMine", Integer.parseInt(page.locator("span.crystalMine > span.level > span").first().textContent()));
        buildingLevels.put("deuteriumSynthesizer", Integer.parseInt(page.locator("span.deuteriumSynthesizer > span.level > span").first().textContent()));
        buildingLevels.put("solarPlant", Integer.parseInt(page.locator("span.solarPlant > span.level > span").first().textContent()));
        buildingLevels.put("fusionPlant", Integer.parseInt(page.locator("span.fusionPlant > span.level > span").first().textContent()));

        buildingLevels.put("metalStorage", Integer.parseInt(page.locator("span.metalStorage > span.level > span").first().textContent()));
        buildingLevels.put("crystalStorage", Integer.parseInt(page.locator("span.crystalStorage > span.level > span").first().textContent()));
        buildingLevels.put("deuteriumStorage", Integer.parseInt(page.locator("span.deuteriumStorage > span.level > span").first().textContent()));
        return this;
    }

    public List<String> listUpgradeable() {
        return page.locator("#producers").locator("li").all()
                .stream()
                .filter(element -> element.locator("button.upgrade").isVisible())
                .map(element -> {
                    String[] classes = element.locator("span").first().getAttribute("class").split(" ");
                    return classes[classes.length - 1];
                })
                .collect(Collectors.toList());
    }

    public ResourcesPage upgradeBuilding(String name) {
        System.out.println(name + " is under construction!");
        page.locator("span." + name + " > button.upgrade").click();
        return this;
    }

    public ResourcesPage constructNeeded() {
        collectResourceData();
        collectResourceBuildingLevels();
        List<String> options = listUpgradeable();
        if (!options.isEmpty()) {

            if (GameDataSingleton.power < 0) {
                if (options.contains("solarPlant"))
                    return upgradeBuilding("solarPlant");
            } else {

                int metal = buildingLevels.get("metalMine");
                int cryst = buildingLevels.get("crystalMine");
                int deute = buildingLevels.get("deuteriumSynthesizer");
                int lowest = Math.min(metal, Math.min(cryst, deute));

                if (metal == lowest && options.contains("metalMine")) return upgradeBuilding("metalMine");
                if (cryst == lowest && options.contains("crystalMine")) return upgradeBuilding("crystalMine");
                if (deute == lowest && options.contains("deuteriumSynthesizer")) return upgradeBuilding("deuteriumSynthesizer");
            }

            if (buildingLevels.get("metalStorage") < 10 && options.contains("metalStorage")) return upgradeBuilding("metalStorage");
            if (buildingLevels.get("crystalStorage") < 10 && options.contains("crystalStorage")) return upgradeBuilding("crystalStorage");
            if (buildingLevels.get("deuteriumStorage") < 10 && options.contains("deuteriumStorage")) return upgradeBuilding("deuteriumStorage");
        }
        return this;
    }
}
