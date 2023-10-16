package org.vulpes.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class EntryPage extends BasePage {
    private final String address = "https://lobby.ogame.gameforge.com/pl_PL/";

    public EntryPage(Page page) {
        super(page);
    }

    public EntryPage open() {
        page.navigate(address);
        return this;
    }

    public EntryPage clickOnLoginTab() {
        page.getByRole(AriaRole.LISTITEM).and(page.getByText("Login")).click();
        return this;
    }

    public EntryPage fillEmail(String email) {
        page.getByRole(AriaRole.TEXTBOX).first().fill(email);
        return this;
    }

    public EntryPage fillPassword(String pass) {
        page.getByRole(AriaRole.TEXTBOX).last().fill(pass);
        return this;
    }

    public EntryPage clickLoginButton() {
        page.getByRole(AriaRole.BUTTON).and(page.getByText("Login")).first().click();
        return this;
    }

    public EntryPage acceptCookies() {
        page.getByRole(AriaRole.BUTTON).and(page.getByText("Zaakceptuj cookie")).click();
        return this;
    }

    public GamePage openLastPlayed() {
        return new GamePage(page.context().waitForPage(() ->
                page.getByRole(AriaRole.BUTTON).and(page.getByText("Ostatnia gra")).click()));
    }
}
