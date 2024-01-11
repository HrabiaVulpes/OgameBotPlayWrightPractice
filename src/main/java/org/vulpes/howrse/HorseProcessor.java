package org.vulpes.howrse;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HorseProcessor {
    private final Page page;
    private final Locator nextHorse;
    private final Locator prevHorse;
    private final Locator work;
    private final Locator forest;
    private final Locator mountain;
    private final Locator beach;
    private final Locator pet;
    private final Locator water;
    private final Locator groom;
    private final Locator feed;
    private final Locator carrot;
    private final Locator mix;
    private final Locator hayNeed;
    private final Locator haySlider;
    private final Locator oatNeed;
    private final Locator oatSlider;
    private final Locator sleep;
    private final Locator energy;
    private final Locator time;
    private final Locator trainingTable;
    private final Locator trainingButtons;
    private final Locator trainingSlider;
    private final Locator trainingData;

    public HorseProcessor(Page page) {
        this.page = page;

        this.prevHorse = page.locator("div.nav").locator("a.left");
        this.nextHorse = page.locator("div.nav").locator("a.right ");
        this.work = page.locator("#mission-tab-0").locator("a.action");
        this.forest = page.locator("#boutonBalade-foret");
        this.mountain = page.locator("#boutonBalade-montagne");
        this.beach = page.locator("#boutonBalade-plage");

        this.pet = page.locator("#boutonCaresser");
        this.water = page.locator("#boutonBoire");
        this.groom = page.locator("#boutonPanser");
        this.feed = page.locator("#boutonNourrir");
        this.carrot = page.locator("#boutonCarotte");
        this.mix = page.locator("#boutonMash");

        this.hayNeed = page.locator("strong.section-fourrage-target");
        this.haySlider = page.locator("#haySlider");

        this.oatNeed = page.locator("strong.section-avoine-target");
        this.oatSlider = page.locator("#oatsSlider");

        this.sleep = page.locator("#boutonCoucher");

        this.energy = page.locator("#status-body-content").locator("#energie");
        this.time = page.locator("span.hour");

        this.trainingTable = page.locator("table.training-table-summary");
        this.trainingButtons = page.locator("table.training-table-summary").locator("td.last").locator("button");
        this.trainingSlider = page.locator("#training-wrapper").locator("li.green");
        this.trainingData = page.locator("#training-wrapper").locator("ul.spacer-small-bottom").locator("li");
    }

    public Boolean standardRoutine() {
        if (!isEnabled(sleep)) return false;

        clickIfEnabled(work);
        clickIfEnabled(pet);
        clickIfEnabled(water);
        clickIfEnabled(groom);
        clickIfEnabled(feed);

        if (page.getByText("Uwaga: jeden z Twoich koni ma niedowagę, musisz mu podać następującą ilość paszy: 20, aby wrócił do formy!").isVisible())
            haySlider.locator("li :text('20')").first().click();
        else haySlider.locator("li :text('" + hayNeed.textContent().trim() + "')").first().click();
        oatSlider.locator("li :text('" + oatNeed.textContent().trim() + "')").first().click();
        page.getByText("Nakarm").click();

        clickIfEnabled(sleep);
        return true;
    }

    public void goNextHorse() {
        clickIfEnabled(nextHorse);
    }

    private void clickIfEnabled(Locator locator) {
        if (isEnabled(locator)) {
            locator.click();
        }
    }

    private boolean isEnabled(Locator locator) {
        return !locator.getAttribute("class").contains("action-disabled");
    }

    private Integer energyLeft() {
        return Integer.parseInt(energy.textContent());
    }

    private Integer currentHour() {
        return Integer.parseInt(time.textContent().split(":")[0]);
    }
}
