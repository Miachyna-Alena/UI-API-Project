package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static aquality.selenium.browser.AqualityServices.getBrowser;

public abstract class BaseTest {

    @BeforeMethod
    public static void setUp() {
        getBrowser().maximize();
    }

    @AfterMethod
    public static void tearDown() {
        getBrowser().quit();
    }
}
