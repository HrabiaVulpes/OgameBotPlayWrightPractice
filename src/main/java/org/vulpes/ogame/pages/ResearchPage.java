package org.vulpes.ogame.pages;

import com.microsoft.playwright.Page;
import org.vulpes.ogame.game.PlayerSettingsSingleton;
import org.vulpes.ogame.game.GameDataSingleton;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ResearchPage extends GamePage {
    public ResearchPage(Page page) {
        super(page);
    }

    public ResearchPage collectResearchLevels() {
        GameDataSingleton.techLevels.clear();
        GameDataSingleton.techLevels.put("energyTechnology", Integer.parseInt(page.locator("span.energyTechnology > span.level > span").first().textContent()));
        GameDataSingleton.techLevels.put("laserTechnology", Integer.parseInt(page.locator("span.laserTechnology > span.level > span").first().textContent()));
        GameDataSingleton.techLevels.put("ionTechnology", Integer.parseInt(page.locator("span.ionTechnology > span.level > span").first().textContent()));
        GameDataSingleton.techLevels.put("hyperspaceTechnology", Integer.parseInt(page.locator("span.hyperspaceTechnology > span.level > span").first().textContent()));
        GameDataSingleton.techLevels.put("plasmaTechnology", Integer.parseInt(page.locator("span.plasmaTechnology > span.level > span").first().textContent()));

        GameDataSingleton.techLevels.put("combustionDriveTechnology", Integer.parseInt(page.locator("span.combustionDriveTechnology > span.level > span").first().textContent()));
        GameDataSingleton.techLevels.put("impulseDriveTechnology", Integer.parseInt(page.locator("span.impulseDriveTechnology > span.level > span").first().textContent()));
        GameDataSingleton.techLevels.put("hyperspaceDriveTechnology", Integer.parseInt(page.locator("span.hyperspaceDriveTechnology > span.level > span").first().textContent()));

        GameDataSingleton.techLevels.put("espionageTechnology", Integer.parseInt(page.locator("span.espionageTechnology > span.level > span").first().textContent()));
        GameDataSingleton.techLevels.put("computerTechnology", Integer.parseInt(page.locator("span.computerTechnology > span.level > span").first().textContent()));
        GameDataSingleton.techLevels.put("astrophysicsTechnology", Integer.parseInt(page.locator("span.astrophysicsTechnology > span.level > span").first().textContent()));
        GameDataSingleton.techLevels.put("researchNetworkTechnology", Integer.parseInt(page.locator("span.researchNetworkTechnology > span.level > span").first().textContent()));
        GameDataSingleton.techLevels.put("gravitonTechnology", Integer.parseInt(page.locator("span.gravitonTechnology > span.level > span").first().textContent()));

        GameDataSingleton.techLevels.put("weaponsTechnology", Integer.parseInt(page.locator("span.weaponsTechnology > span.level > span").first().textContent()));
        GameDataSingleton.techLevels.put("shieldingTechnology", Integer.parseInt(page.locator("span.shieldingTechnology > span.level > span").first().textContent()));
        GameDataSingleton.techLevels.put("armorTechnology", Integer.parseInt(page.locator("span.armorTechnology > span.level > span").first().textContent()));
        return this;
    }

    public List<String> listUpgradeable() {
        return page.locator("#technologies").locator("li").all()
                .stream()
                .filter(element -> element.locator("button.upgrade").isVisible())
                .map(element -> {
                    String[] classes = element.locator("span").first().getAttribute("class").split(" ");
                    return classes[classes.length - 1];
                })
                .collect(Collectors.toList());
    }

    public ResearchPage upgradeResearch(String name) {
        System.out.println("Researching: " + name);
        page.locator("span." + name + " > button.upgrade").click();
        return this;
    }

    public ResearchPage upgradeLowestUsable() {
        collectResearchLevels();
        List<String> options = listUpgradeable();
        if (GameDataSingleton.techLevels.get("laserTechnology") >= 12) options.remove("laserTechnology");
        if (GameDataSingleton.techLevels.get("ionTechnology") >= 25) options.remove("ionTechnology");
        if (GameDataSingleton.techLevels.get("gravitonTechnology") >= 1) options.remove("gravitonTechnology");
        if (!PlayerSettingsSingleton.fleeter) {
            options = options.stream()
                    .filter(tech -> !tech.contains("Drive"))
                    .collect(Collectors.toList());
        }
        if (!options.isEmpty()) {
            options.sort(Comparator.comparing(a -> GameDataSingleton.techLevels.get(a)));
            upgradeResearch(options.get(0));
        }
        return this;
    }
}
