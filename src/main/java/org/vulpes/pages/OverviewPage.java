package org.vulpes.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class OverviewPage extends GamePage{
    public OverviewPage(Page page) {
        super(page);
    }

    public boolean isConstructing(){
        return page.locator("div.productionboxbuilding table.active tr.data").all().stream().anyMatch(Locator::isVisible);
    }

    public boolean isConstructingLifeforms(){
        return page.locator("div.productionboxlfbuilding table.active tr.data").all().stream().anyMatch(Locator::isVisible);
    }

    public boolean isResearching(){
        return page.locator("#productionboxresearchcomponent table.active tr.data").all().stream().anyMatch(Locator::isVisible);
    }

    public boolean isResearchingLifeforms(){
        return page.locator("#productionboxlfresearchcomponent table.active tr.data").all().stream().anyMatch(Locator::isVisible);
    }
    public boolean isProducing(){
        return page.locator("#productionboxshipyardcomponent table.active tr.data").all().stream().anyMatch(Locator::isVisible);
    }
}
