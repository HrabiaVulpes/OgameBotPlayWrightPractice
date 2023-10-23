package org.vulpes.pages;

import com.microsoft.playwright.Page;
import org.vulpes.game.GameDataSingleton;

import java.util.List;
import java.util.stream.Collectors;

public class DefencesPage extends GamePage {
    public DefencesPage(Page page) {
        super(page);
    }

    public DefencesPage collectDefencesCounts(){
        GameDataSingleton.defencesCounts.clear();
        GameDataSingleton.defencesCounts.put("rocketLauncher", Integer.parseInt(page.locator("span.rocketLauncher > span.amount").first().textContent()));
        GameDataSingleton.defencesCounts.put("laserCannonLight", Integer.parseInt(page.locator("span.laserCannonLight > span.amount").first().textContent()));
        GameDataSingleton.defencesCounts.put("laserCannonHeavy", Integer.parseInt(page.locator("span.laserCannonHeavy > span.amount").first().textContent()));
        GameDataSingleton.defencesCounts.put("gaussCannon", Integer.parseInt(page.locator("span.gaussCannon > span.amount").first().textContent()));
        GameDataSingleton.defencesCounts.put("ionCannon", Integer.parseInt(page.locator("span.ionCannon > span.amount").first().textContent()));
        GameDataSingleton.defencesCounts.put("plasmaCannon", Integer.parseInt(page.locator("span.plasmaCannon > span.amount").first().textContent()));
        GameDataSingleton.defencesCounts.put("shieldDomeSmall", Integer.parseInt(page.locator("span.shieldDomeSmall > span.amount").first().textContent()));
        GameDataSingleton.defencesCounts.put("shieldDomeLarge", Integer.parseInt(page.locator("span.shieldDomeLarge > span.amount").first().textContent()));
        GameDataSingleton.defencesCounts.put("missileInterceptor", Integer.parseInt(page.locator("span.missileInterceptor > span.amount").first().textContent()));
        GameDataSingleton.defencesCounts.put("missileInterplanetary", Integer.parseInt(page.locator("span.missileInterplanetary > span.amount").first().textContent()));

        return this;
    }

    public List<String> listBuildable(){
        return page.locator("li.technology").all()
                .stream()
                .filter(element -> element.getAttribute("data-status").equals("on"))
                .map(element -> {
                    String[] classes = element.locator("span.icon").first().getAttribute("class").split(" ");
                    return classes[classes.length-1];
                })
                .collect(Collectors.toList());
    }

    public DefencesPage buildDefence(String name){
        page.locator("li." + name).click();
        page.locator("div.build-it_wrap button.upgrade").click();
        return this;
    }
}
