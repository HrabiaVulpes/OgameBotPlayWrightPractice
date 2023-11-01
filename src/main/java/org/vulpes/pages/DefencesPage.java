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
        defencesCounts.put("rocketLauncher", Integer.parseInt(page.locator("span.rocketLauncher > span.amount").first().getAttribute("data-value")));
        defencesCounts.put("laserCannonLight", Integer.parseInt(page.locator("span.laserCannonLight > span.amount").first().getAttribute("data-value")));
        defencesCounts.put("laserCannonHeavy", Integer.parseInt(page.locator("span.laserCannonHeavy > span.amount").first().getAttribute("data-value")));
        defencesCounts.put("gaussCannon", Integer.parseInt(page.locator("span.gaussCannon > span.amount").first().getAttribute("data-value")));
        defencesCounts.put("ionCannon", Integer.parseInt(page.locator("span.ionCannon > span.amount").first().getAttribute("data-value")));
        defencesCounts.put("plasmaCannon", Integer.parseInt(page.locator("span.plasmaCannon > span.amount").first().getAttribute("data-value")));
        defencesCounts.put("shieldDomeSmall", Integer.parseInt(page.locator("span.shieldDomeSmall > span.amount").first().getAttribute("data-value")));
        defencesCounts.put("shieldDomeLarge", Integer.parseInt(page.locator("span.shieldDomeLarge > span.amount").first().getAttribute("data-value")));
        defencesCounts.put("missileInterceptor", Integer.parseInt(page.locator("span.missileInterceptor > span.amount").first().getAttribute("data-value")));
        defencesCounts.put("missileInterplanetary", Integer.parseInt(page.locator("span.missileInterplanetary > span.amount").first().getAttribute("data-value")));

        return this;
    }

    public List<String> listBuildable() {
        return page.locator("li.technology").all().stream().filter(element -> element.getAttribute("data-status").equals("on")).map(element -> {
            String[] classes = element.locator("span.icon").first().getAttribute("class").split(" ");
            return classes[classes.length - 1];
        }).collect(Collectors.toList());
    }

    public DefencesPage buildDefence(String name) {
        System.out.println(name + " joins defences!");
        page.locator("li." + name).click();
        page.locator("div.build-it_wrap button.upgrade").click();
        return this;
    }

    public DefencesPage buildDefence(String name, Integer amount) {
        System.out.println(name + " joins defences!");
        page.locator("li." + name).click();
        page.locator("#build_amount").fill(amount.toString());
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

            if (defencesCounts.get("shieldDomeLarge") == 0 && options.contains("shieldDomeLarge")) return buildDefence("shieldDomeLarge");
            if (defencesCounts.get("shieldDomeSmall") == 0 && options.contains("shieldDomeSmall")) return buildDefence("shieldDomeSmall");
            if (options.contains("missileInterceptor")) return buildDefence("missileInterceptor");
            if (defencesCounts.get("plasmaCannon") < expected.get("plasmaCannon") && options.contains("plasmaCannon")) return buildDefence("plasmaCannon");
            if (defencesCounts.get("gaussCannon") < expected.get("gaussCannon") && options.contains("gaussCannon")) return buildDefence("gaussCannon", 2);
            if (defencesCounts.get("ionCannon") < expected.get("ionCannon") && options.contains("ionCannon")) return buildDefence("ionCannon", 4);
            if (defencesCounts.get("laserCannonHeavy") < expected.get("laserCannonHeavy") && options.contains("laserCannonHeavy")) return buildDefence("laserCannonHeavy", 8);
            if (defencesCounts.get("laserCannonLight") < expected.get("laserCannonLight") && options.contains("laserCannonLight")) return buildDefence("laserCannonLight", 16);
            if (defencesCounts.get("rocketLauncher") < expected.get("rocketLauncher") && options.contains("rocketLauncher")) return buildDefence("rocketLauncher", 32);
            if (options.contains("plasmaCannon")) return buildDefence("plasmaCannon");
        }
        return this;
    }
}
