package org.vulpes.pages;

import com.microsoft.playwright.Page;
import org.vulpes.game.GameDataSingleton;

import java.util.List;
import java.util.stream.Collectors;

public class FacilitiesPage extends GamePage{
    public FacilitiesPage(Page page) {
        super(page);
    }

    public FacilitiesPage collectFacilitiesBuildingLevels(){
        GameDataSingleton.buildingLevels.clear();
        GameDataSingleton.buildingLevels.put("roboticsFactory", Integer.parseInt(page.locator("span.roboticsFactory > span.level > span").first().textContent()));
        GameDataSingleton.buildingLevels.put("shipyard", Integer.parseInt(page.locator("span.shipyard > span.level > span").first().textContent()));
        GameDataSingleton.buildingLevels.put("researchLaboratory", Integer.parseInt(page.locator("span.researchLaboratory > span.level > span").first().textContent()));
        GameDataSingleton.buildingLevels.put("allianceDepot", Integer.parseInt(page.locator("span.allianceDepot > span.level > span").first().textContent()));
        GameDataSingleton.buildingLevels.put("missileSilo", Integer.parseInt(page.locator("span.missileSilo > span.level > span").first().textContent()));
        GameDataSingleton.buildingLevels.put("naniteFactory", Integer.parseInt(page.locator("span.naniteFactory > span.level > span").first().textContent()));
        GameDataSingleton.buildingLevels.put("terraformer", Integer.parseInt(page.locator("span.terraformer > span.level > span").first().textContent()));
        GameDataSingleton.buildingLevels.put("repairDock", Integer.parseInt(page.locator("span.repairDock > span.level > span").first().textContent()));

        return this;
    }

    public List<String> listUpgradeable(){
        return page.locator("#technologies").locator("li").all()
                .stream()
                .filter(element -> element.locator("button.upgrade").isVisible())
                .map(element -> {
                    String[] classes = element.locator("span").first().getAttribute("class").split(" ");
                    return classes[classes.length-1];
                })
                .collect(Collectors.toList());
    }

    public FacilitiesPage upgradeBuilding(String name){
        page.locator("span." + name + " > button.upgrade").click();
        return this;
    }
}
