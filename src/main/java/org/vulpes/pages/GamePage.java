package org.vulpes.pages;

import com.microsoft.playwright.Page;
import org.vulpes.game.GameDataSingleton;

public class GamePage extends BasePage{

    public GamePage(Page page) {
        super(page);
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

    public GamePage openResearch(){
        page.getByText("Badania").click();
        return this;
    }

    public GamePage openShipyard(){
        page.getByText("Stocznia").click();
        return this;
    }

    public GamePage openDefences(){
        page.getByText("Obrona").click();
        return this;
    }

    public GamePage openFleet(){
        page.getByText("Flota").click();
        return this;
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
                .getAttribute("data-raw"));

        GameDataSingleton.food = Integer.parseInt(page.locator("#resources_food")
                .first()
                .getAttribute("data-raw")
                .split("\\.")[0]
        );
        return this;
    }
}
