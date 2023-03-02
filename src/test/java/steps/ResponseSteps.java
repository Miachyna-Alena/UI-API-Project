package steps;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import models.TestModel;
import pages.ProjectPage;
import pages.ProjectsPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResponseSteps {
    private static String name;
    private static final ProjectsPage PROJECTS_PAGE = new ProjectsPage();
    private static final ProjectPage PROJECT_PAGE = new ProjectPage(name);

    private static final ISettingsFile CONFIG_DATA = new JsonSettingsFile("data/config-data.json");
    private static final int START_TIME_COLUMN_INDEX = Integer.parseInt(CONFIG_DATA.getValue("/START_TIME_COLUMN_INDEX").toString());
    private static final int TEST_NAME_COLUMN_INDEX = Integer.parseInt(CONFIG_DATA.getValue("/TEST_NAME_COLUMN_INDEX").toString());

    public static String getProjectID(String name) {
        String link = PROJECTS_PAGE.getProjectLink(name).getHref();
        return link.substring(link.lastIndexOf("=") + 1);
    }

    public static boolean isVariantNumberInTheFooterCorrect(int variant) {
        return PROJECTS_PAGE.getVersionNumberFromFooter() == variant;
    }

    public static boolean isTestsListSortedByDateDESC() {
        List<String> dates = PROJECT_PAGE.getListOfColumnData(START_TIME_COLUMN_INDEX);
        List<String> sortedDates = new ArrayList<>(dates);
        sortedDates.sort(Collections.reverseOrder());
        return sortedDates.equals(dates);
    }

    public static List<String> getListOfTestNamesFromAPIResponse(String projectID) {
        List<TestModel> test_names = APISteps.getTestsList(projectID);
        List<String> test_names_text = new ArrayList<>();
        test_names.forEach(element -> test_names_text.add(element.getName()));
        return test_names_text;
    }

    public static List<String> getListOfTestNames() {
        return PROJECT_PAGE.getListOfColumnData(TEST_NAME_COLUMN_INDEX);
    }

    public static boolean isTestsFromAPIEqualsToTestsFromSite(String projectID) {
        return getListOfTestNamesFromAPIResponse(projectID).containsAll(getListOfTestNames());
    }
}
