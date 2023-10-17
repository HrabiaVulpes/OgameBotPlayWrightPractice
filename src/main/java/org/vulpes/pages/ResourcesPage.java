package org.vulpes.pages;

import com.microsoft.playwright.Page;
import org.vulpes.game.GameDataSingleton;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResourcesPage extends GamePage{
    public ResourcesPage(Page page) {
        super(page);
    }

    public ResourcesPage collectResourceBuildingLevels(){
        GameDataSingleton.buildingLevels.put("metalMine", Integer.parseInt(page.locator("span.metalMine > span.level > span").first().textContent()));
        GameDataSingleton.buildingLevels.put("crystalMine", Integer.parseInt(page.locator("span.crystalMine > span.level > span").first().textContent()));
        GameDataSingleton.buildingLevels.put("deuteriumSynthesizer", Integer.parseInt(page.locator("span.deuteriumSynthesizer > span.level > span").first().textContent()));
        GameDataSingleton.buildingLevels.put("solarPlant", Integer.parseInt(page.locator("span.solarPlant > span.level > span").first().textContent()));
        GameDataSingleton.buildingLevels.put("fusionPlant", Integer.parseInt(page.locator("span.fusionPlant > span.level > span").first().textContent()));

        GameDataSingleton.buildingLevels.put("metalStorage", Integer.parseInt(page.locator("span.metalStorage > span.level > span").first().textContent()));
        GameDataSingleton.buildingLevels.put("crystalStorage", Integer.parseInt(page.locator("span.crystalStorage > span.level > span").first().textContent()));
        GameDataSingleton.buildingLevels.put("deuteriumStorage", Integer.parseInt(page.locator("span.deuteriumStorage > span.level > span").first().textContent()));
        return this;
    }

    public List<String> listUpgradeable(){
        return page.locator("#producers").locator("li").all()
                .stream()
                .filter(element -> element.locator("button.upgrade").isVisible())
                .map(element -> {
                    String[] classes = element.locator("span").first().getAttribute("class").split(" ");
                    return classes[classes.length-1];
                })
                .collect(Collectors.toList());
    }

    public ResourcesPage upgradeBuilding(String name){
        page.locator("span." + name + " > button.upgrade").click();
        return this;
    }
}
