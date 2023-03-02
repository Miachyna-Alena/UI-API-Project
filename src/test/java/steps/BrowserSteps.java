package steps;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.openqa.selenium.Cookie;

import static aquality.selenium.browser.AqualityServices.getBrowser;

public class BrowserSteps {
    private static final ISettingsFile CREDENTIAL_DATA = new JsonSettingsFile("data/credentials-data.json");
    private static final ISettingsFile CONFIG_DATA = new JsonSettingsFile("data/config-data.json");

    private static final String URL = CONFIG_DATA.getValue("/BASE_URL").toString();
    private static final String LOGIN = CREDENTIAL_DATA.getValue("/LOGIN").toString();
    private static final String PASSWORD = CREDENTIAL_DATA.getValue("/PASSWORD").toString();
    private static final String AUTHORIZATION_URL = String.format("http://%1$s:%2$s@%3$s", LOGIN, PASSWORD, URL);
    public static void navigateToSite() {
        getBrowser().goTo(AUTHORIZATION_URL);
        getBrowser().waitForPageToLoad();
    }

    public static void addTokenCookie(String token) {
        getBrowser().getDriver().manage().addCookie(new Cookie("token", token));
    }

    public static void refreshPage() {
        getBrowser().refresh();
    }

    public static void goToPreviousPage() {
        getBrowser().goBack();
    }

    public static void switchToLastTabs() {
        getBrowser().tabs().switchToLastTab();
    }

    public static void closePopUp() {
        getBrowser().executeScript("window.close()");
    }
}
