package tests;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import kong.unirest.HttpResponse;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.Test;
import pages.ProjectPage;
import pages.ProjectsPage;
import pages.forms.AddProjectForm;
import steps.APISteps;
import steps.BrowserSteps;
import steps.ResponseSteps;
import utils.RandomUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class UIAPITest extends BaseTest {
    private static final Logger LOGGER = AqualityServices.getLogger();
    protected static final ISettingsFile TEST_DATA = new JsonSettingsFile("data/test-data.json");
    private static final String FORMAT_TYPE = TEST_DATA.getValue("/FORMAT_TYPE").toString();
    private static final String PROJECT_NAME = TEST_DATA.getValue("/PROJECT_NAME").toString();
    private static final int VARIANT = Integer.parseInt(TEST_DATA.getValue("/VARIANT").toString());
    private static final int PROJECT_NAME_LENGTH = Integer.parseInt(TEST_DATA.getValue("/PROJECT_NAME_LENGTH").toString());
    private static final String NEW_PROJECT_NAME = RandomUtils.generateRandoString(PROJECT_NAME_LENGTH);
    private static final String TEST_NAME = TEST_DATA.getValue("/TEST_NAME").toString();
    private static final String ENV = TEST_DATA.getValue("/ENV").toString();
    private static final String METHOD_NAME = TEST_DATA.getValue("/METHOD_NAME").toString();
    private static final String BROWSER = TEST_DATA.getValue("/BROWSER").toString();
    private static final int LOG_CONTENT_LENGTH = Integer.parseInt(TEST_DATA.getValue("/LOG_CONTENT_LENGTH").toString());
    private static final String LOG_CONTENT = RandomUtils.generateRandoString(LOG_CONTENT_LENGTH);
    private static final String CONTENT_TYPE = TEST_DATA.getValue("/CONTENT_TYPE").toString();

    @Test
    public void testTest() throws JsonProcessingException {
        LOGGER.info("Step 1: Request to API to get a token according to the variant number.");
        String token = APISteps.getToken(VARIANT);
        System.out.println(token);
        assertFalse(token.isEmpty(), "Token is EMPTY.");

        LOGGER.info("Step 2: Go to site. Pass the necessary authorization.");
        BrowserSteps.navigateToSite();
        LOGGER.info("Step 2: Using cookie, pass the token generated in Step 1 (the token parameter).");
        BrowserSteps.addTokenCookie(token);
        ProjectsPage projectsPage = new ProjectsPage();
        assertTrue(projectsPage.state().waitForDisplayed(), String.format("%s is NOT open.", projectsPage.getName()));
        String projectId = ResponseSteps.getProjectID(PROJECT_NAME);
        LOGGER.info("Step 2: Refresh the page.");
        BrowserSteps.refreshPage();
        assertTrue(ResponseSteps.isVariantNumberInTheFooterCorrect(VARIANT), String.format("After refreshing the %s, the variant number in the footer is INCORRECT.", projectsPage.getName()));

        LOGGER.info("Step 3: Go to the \"Nexage Project\" page.");
        projectsPage.clickProjectLink(PROJECT_NAME);
        ProjectPage projectPage = new ProjectPage(PROJECT_NAME);
        assertTrue(projectPage.state().waitForDisplayed(), String.format("%s is NOT open.", projectPage.getName()));
        assertTrue(ResponseSteps.isTestsListSortedByDateDESC(), "Tests List sorted NOT by date DESC.");
        LOGGER.info(String.format("Step 3: API request to get a list of tests in %s format.", FORMAT_TYPE));
        assertTrue(ResponseSteps.isTestsFromAPIEqualsToTestsFromSite(projectId), "The tests on the main page do NOT match the tests from the API request.");

        LOGGER.info(String.format("Step 4: Return to the previous page in the browser (%s).", projectsPage.getName()));
        BrowserSteps.goToPreviousPage();
        projectsPage.clickAddProjectButton();
        BrowserSteps.switchToLastTabs();
        LOGGER.info("Step 4: Click on \"+Add\". Enter a project name and save.");
        AddProjectForm addProjectForm = new AddProjectForm();
        addProjectForm.inputProjectName(NEW_PROJECT_NAME);
        addProjectForm.clickSaveProjectButton();
        assertTrue(addProjectForm.isSuccessAlertDisplayed(), "After saving the project, successful message is NOT appear.");
        LOGGER.info("Step 4: Close the window for adding a project, call the js-method closePopUp().");
        BrowserSteps.closePopUp();
        assertFalse(addProjectForm.state().waitForDisplayed(), String.format("%s is OPEN", addProjectForm.getName()));
        BrowserSteps.switchToLastTabs();
        LOGGER.info("Step 4: Refresh the page.");
        BrowserSteps.refreshPage();
        assertTrue(projectsPage.isNewProjectInProjectsList(PROJECT_NAME));

        LOGGER.info("Step 5: Go to the page of the created project.");
        projectsPage.clickProjectLink(NEW_PROJECT_NAME);
        LOGGER.info("Step 5: Add test via API (along with a log and a screenshot of the current page).");
        projectPage = new ProjectPage(NEW_PROJECT_NAME);
        HttpResponse<String> response = APISteps.addNewTest(new Date().toString(), NEW_PROJECT_NAME, TEST_NAME, METHOD_NAME, ENV, new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date()), BROWSER);
        int testID = Integer.parseInt(response.getBody());
        APISteps.sendingLogsTest(testID, LOG_CONTENT);
        APISteps.sendingAttachmentTest(testID, ((TakesScreenshot) getBrowser().getDriver()).getScreenshotAs(OutputType.BASE64), CONTENT_TYPE);
        assertTrue(projectPage.isTestDisplay(testID));
    }
}
