package org.vulpes.pages;

import com.microsoft.playwright.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.vulpes.game.GameDataSingleton.defencesCounts;

public class DefencesPage extends GamePage {
    public DefencesPage(Page page) {
        super(page);
    }

    public DefencesPage collectDefencesCounts() {
        defencesCounts.clear();
        defencesCounts.put("rocketLauncher", Integer.parseInt(page.locator("span.rocketLauncher > span.amount").first().textContent()));
        defencesCounts.put("laserCannonLight", Integer.parseInt(page.locator("span.laserCannonLight > span.amount").first().textContent()));
        defencesCounts.put("laserCannonHeavy", Integer.parseInt(page.locator("span.laserCannonHeavy > span.amount").first().textContent()));
        defencesCounts.put("gaussCannon", Integer.parseInt(page.locator("span.gaussCannon > span.amount").first().textContent()));
        defencesCounts.put("ionCannon", Integer.parseInt(page.locator("span.ionCannon > span.amount").first().textContent()));
        defencesCounts.put("plasmaCannon", Integer.parseInt(page.locator("span.plasmaCannon > span.amount").first().textContent()));
        defencesCounts.put("shieldDomeSmall", Integer.parseInt(page.locator("span.shieldDomeSmall > span.amount").first().textContent()));
        defencesCounts.put("shieldDomeLarge", Integer.parseInt(page.locator("span.shieldDomeLarge > span.amount").first().textContent()));
        defencesCounts.put("missileInterceptor", Integer.parseInt(page.locator("span.missileInterceptor > span.amount").first().textContent()));
        defencesCounts.put("missileInterplanetary", Integer.parseInt(page.locator("span.missileInterplanetary > span.amount").first().textContent()));

        return this;
    }

    public List<String> listBuildable() {
        return page.locator("li.technology").all()
                .stream()
                .filter(element -> element.getAttribute("data-status").equals("on"))
                .map(element -> {
                    String[] classes = element.locator("span.icon").first().getAttribute("class").split(" ");
                    return classes[classes.length - 1];
                })
                .collect(Collectors.toList());
    }

    public DefencesPage buildDefence(String name) {
        page.locator("li." + name).click();
        page.locator("div.build-it_wrap button.upgrade").click();
        return this;
    }

    public DefencesPage constructNeeded() {
        collectDefencesCounts();
        List<String> options = listBuildable();
        if (!options.isEmpty()) {
            Map<String, Integer> expected = new HashMap<>();
            int plasmas = defencesCounts.getOrDefault("plasmaCannon", 1);

            expected.put("plasmaCannon", plasmas);
            expected.put("rocketLauncher", plasmas * 270);
            expected.put("laserCannonLight", plasmas * 180);
            expected.put("laserCannonHeavy", plasmas * 45);
            expected.put("gaussCannon", plasmas * 5);
            expected.put("ionCannon", plasmas * 15);

            if (defencesCounts.get("shieldDomeLarge") == 0 && options.contains("shieldDomeLarge")){
                buildDefence("shieldDomeLarge");
                return this;
            }
            if (defencesCounts.get("shieldDomeSmall") == 0 && options.contains("shieldDomeSmall")){
                buildDefence("shieldDomeSmall");
                return this;
            }
            if (options.contains("missileInterceptor")){
                buildDefence("missileInterceptor");
                return this;
            }
            if (defencesCounts.get("plasmaCannon") < expected.get("plasmaCannon") && options.contains("plasmaCannon")){
                buildDefence("plasmaCannon");
                return this;
            }
            if (defencesCounts.get("gaussCannon") < expected.get("gaussCannon") && options.contains("gaussCannon")){
                buildDefence("gaussCannon");
                return this;
            }
            if (defencesCounts.get("ionCannon") < expected.get("ionCannon") && options.contains("ionCannon")){
                buildDefence("ionCannon");
                return this;
            }
            if (defencesCounts.get("laserCannonHeavy") < expected.get("laserCannonHeavy") && options.contains("laserCannonHeavy")){
                buildDefence("laserCannonHeavy");
                return this;
            }
            if (defencesCounts.get("laserCannonLight") < expected.get("laserCannonLight") && options.contains("laserCannonLight")){
                buildDefence("laserCannonLight");
                return this;
            }
            if (defencesCounts.get("rocketLauncher") < expected.get("rocketLauncher") && options.contains("rocketLauncher")){
                buildDefence("rocketLauncher");
                return this;
            }
            if (options.contains("plasmaCannon"))
                buildDefence("plasmaCannon");

        }
        return this;
    }
}
