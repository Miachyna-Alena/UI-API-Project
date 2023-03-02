package steps;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import models.TestModel;
import utils.APIUtils;

import java.util.List;

public class APISteps {
    private static final Logger LOGGER = AqualityServices.getLogger();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ISettingsFile CONFIG_DATA = new JsonSettingsFile("data/config-data.json");
    private static final String BASE_PATH = CONFIG_DATA.getValue("/BASE_PATH").toString();
    private static final String GET_TOKEN_PATH = CONFIG_DATA.getValue("/GET_TOKEN_PATH").toString();
    public static final String GET_TOKEN = String.format(BASE_PATH, GET_TOKEN_PATH);
    private static final String GET_TESTS_PATH = CONFIG_DATA.getValue("/GET_TESTS_PATH").toString();
    private static final String GET_TESTS = String.format(BASE_PATH, GET_TESTS_PATH);
    private static final String VARIANT = CONFIG_DATA.getValue("/VARIANT").toString();
    private static final String PROJECT_ID = CONFIG_DATA.getValue("/PROJECT_ID").toString();
    private static final String ADD_TEST_PATH = CONFIG_DATA.getValue("/ADD_TEST_PATH").toString();
    private static final String ADD_TEST = String.format(BASE_PATH, ADD_TEST_PATH);
    private static final String SID = CONFIG_DATA.getValue("/SID").toString();
    private static final String PROJECT_NAME = CONFIG_DATA.getValue("/PROJECT_NAME").toString();
    private static final String TEST_NAME = CONFIG_DATA.getValue("/TEST_NAME").toString();
    private static final String METHOD_NAME = CONFIG_DATA.getValue("/METHOD_NAME").toString();
    private static final String ENV = CONFIG_DATA.getValue("/ENV").toString();
    private static final String START_TIME = CONFIG_DATA.getValue("/START_TIME").toString();
    private static final String BROWSER = CONFIG_DATA.getValue("/BROWSER").toString();
    private static final String SENT_LOG_PATH = CONFIG_DATA.getValue("/SENT_LOG_PATH").toString();
    private static final String SENT_LOG = String.format(BASE_PATH, SENT_LOG_PATH);
    private static final String TEST_ID = CONFIG_DATA.getValue("/TEST_ID").toString();
    private static final String CONTENT = CONFIG_DATA.getValue("/CONTENT").toString();
    private static final String SENT_ATTACHMENT_PATH = CONFIG_DATA.getValue("/SENT_ATTACHMENT_PATH").toString();
    private static final String SENT_ATTACHMENT = String.format(BASE_PATH, SENT_ATTACHMENT_PATH);
    private static final String CONTENT_TYPE = CONFIG_DATA.getValue("/CONTENT_TYPE").toString();

    public static String getToken(int variant) {
        return APIUtils.post(GET_TOKEN)
                .field(VARIANT, variant)
                .asString()
                .getBody();
    }

    public static List<TestModel> getTestsList(String projectId) {
        try {
            String jsonArray = APIUtils.post(GET_TESTS).field(PROJECT_ID, projectId).asString().getBody();
            return objectMapper.readValue(jsonArray, new TypeReference<List<TestModel>>() {
            });
        } catch (JsonProcessingException exception) {
            LOGGER.info(exception.getMessage());
            return null;
        }
    }

    public static HttpResponse<String> addNewTest(String sid,
                                                  String projectName,
                                                  String testName,
                                                  String methodName,
                                                  String env,
                                                  String startTimeString,
                                                  String browser) {
        return APIUtils.post(ADD_TEST)
                .field(SID, sid)
                .field(PROJECT_NAME, projectName)
                .field(TEST_NAME, testName)
                .field(METHOD_NAME, methodName)
                .field(ENV, env)
                .field(START_TIME, startTimeString)
                .field(BROWSER, browser)
                .asString();
    }

    public static HttpResponse<String> sendingLogsTest(int testId, String content) {
        return APIUtils.post(SENT_LOG)
                .field(TEST_ID, testId).field(CONTENT, content).asString();
    }

    public static HttpResponse<String> sendingAttachmentTest(int testId, String content, String contentType) {
        return APIUtils.post(SENT_ATTACHMENT)
                .field(TEST_ID, testId)
                .field(CONTENT, content)
                .field(CONTENT_TYPE, contentType).asString();
    }
}
