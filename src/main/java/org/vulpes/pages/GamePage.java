package org.vulpes.pages;

import com.microsoft.playwright.Page;
import org.vulpes.game.GameDataSingleton;

public class GamePage extends BasePage{

    public GamePage(Page page) {
        super(page);
    }

    public OverviewPage openOverview(){
        page.getByText("Podgląd").click();
        return new OverviewPage(page);
    }

    public ResourcesPage openResources(){
        page.getByText("Surowce").click();
        return new ResourcesPage(page);
    }

    public GamePage openLifeforms(){
        page.getByText("Forma życia").click();
        return this;
    }

    public FacilitiesPage openFacilities(){
        page.getByText("Stacja").click();
        return new FacilitiesPage(page);
    }

    public GamePage openTrader(){
        page.getByText("Handlarz").click();
        return this;
    }

    public ResearchPage openResearch(){
        page.getByText("Badania").and(page.locator("span.textlabel")).click();
        return new ResearchPage(page);
    }

    public GamePage openShipyard(){
        page.getByText("Stocznia").click();
        return this;
    }

    public DefencesPage openDefences(){
        page.getByText("Obrona").click();
        return new DefencesPage(page);
    }

    public FleetPage openFleet(){
        page.getByText("Flota").click();
        return new FleetPage(page);
    }

    public GamePage openGalaxy(){
        page.getByText("Galaktyka").click();
        return this;
    }

    public GamePage openEmpire(){
        page.getByText("Imperium").click();
        return this;
    }

    public GamePage openAlliance(){
        page.getByText("Sojusz").click();
        return this;
    }

    public GamePage openPremium(){
        page.getByText("Kantyna").click();
        return this;
    }

    public GamePage openShop(){
        page.getByText("Sklep").click();
        return this;
    }

    public GamePage collectResourceData(){
        GameDataSingleton.metal = Integer.parseInt(page.locator("#resources_metal")
                .first()
                .getAttribute("data-raw"));

        GameDataSingleton.crystal = Integer.parseInt(page.locator("#resources_crystal")
                .first()
                .getAttribute("data-raw"));

        GameDataSingleton.deuter = Integer.parseInt(page.locator("#resources_deuterium")
                .first()
                .getAttribute("data-raw"));

        GameDataSingleton.power = Integer.parseInt(page.locator("#resources_energy")
                .first()
                .getAttribute("data-raw"));

        GameDataSingleton.population = Integer.parseInt(page.locator("#resources_population")
                        .first()
                        .getAttribute("data-raw")
                        .split("\\.")[0]
        );

        GameDataSingleton.food = Integer.parseInt(page.locator("#resources_food")
                .first()
                .getAttribute("data-raw")
                .split("\\.")[0]
        );
        return this;
    }
}
