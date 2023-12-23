package org.vulpes.ogame.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;
import java.util.stream.Collectors;

public class FleetSendPage extends GamePage{
    public FleetSendPage(Page page) {
        super(page);
    }

    public FleetSendPage useCoordinates(Integer galaxy, Integer system, Integer planet){
        page.locator("div.coordsSection > input.galaxy").click();
        page.keyboard().type(galaxy.toString());
        page.locator("div.coordsSection > input.system").click();
        page.keyboard().type(system.toString());
        page.locator("div.coordsSection > input.planet").click();
        page.keyboard().type(planet.toString());
        return this;
    }

    public List<String> availableMissions(){
        return page.locator("#missions > li.on").all()
                .stream()
                .map(Locator::textContent)
                .collect(Collectors.toList());
    }

    public FleetSendPage pickMission(String name){
        page.locator("#missions > li.on > a > span").and(page.getByText(name)).click();
        return this;
    }

    public FleetPage send(){
        page.locator("#naviActions > a.start").click();
        return new FleetPage(page);
    }
}
