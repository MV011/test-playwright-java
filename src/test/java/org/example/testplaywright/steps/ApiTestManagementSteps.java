package org.example.testplaywright.steps;

import com.microsoft.playwright.APIResponse;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.example.testplaywright.api.client.PlaywrightApiClient;
import org.example.testplaywright.context.ScenarioContext;
import org.example.testplaywright.models.request.CreateTestCaseRequest;
import org.example.testplaywright.models.response.CurrentUserResponse;
import org.example.testplaywright.models.response.TestCaseResponse;
import org.example.testplaywright.utils.ApiUtils;
import org.example.testplaywright.utils.CucumberUtils;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;

public class ApiTestManagementSteps {

    private final PlaywrightApiClient playwrightApiClient;
    private final ScenarioContext scenarioContext;

    public ApiTestManagementSteps(PlaywrightApiClient playwrightApiClient, ScenarioContext scenarioContext) {
        this.playwrightApiClient = playwrightApiClient;
        this.scenarioContext = scenarioContext;
    }

    @Given("^I have a payload set for CreateTestCase with the following data:$")
    public void iHaveAPayloadSetForCreateTestCaseWithTheFollowingData(DataTable table) {
        List<Map<String, String>> data = CucumberUtils.processDataTable(table);

        CreateTestCaseRequest request =
                CreateTestCaseRequest.builder()
                        .title(data.getFirst().get("title"))
                        .description(data.getFirst().get("description"))
                        .status(data.getFirst().get("status")).build();

        scenarioContext.setContextValue("payload", request);
    }

    @And("^the response should contain the (created|updated) test case")
    public void theResponseShouldContainTheCreatedTestCase(String status) {
        APIResponse response = scenarioContext.getContextValue("response");
        CreateTestCaseRequest request = scenarioContext.getContextValue("payload");
        TestCaseResponse body = ApiUtils.convertResponseBody(response, TestCaseResponse.class);

        SoftAssert asserts = new SoftAssert();
        asserts.assertEquals(body.getTitle(), request.getTitle(), "Title should be equal");
        asserts.assertEquals(body.getDescription(), request.getDescription(), "Description should be equal");
        asserts.assertEquals(body.getStatus(), request.getStatus(), "Status should be equal");
        asserts.assertAll();
    }

    @And("the response should contain a list of test cases that belong to the current user")
    public void theResponseShouldContainAListOfTestCases() {
        APIResponse response = scenarioContext.getContextValue("response");
        List<TestCaseResponse> body = ApiUtils.deserializeJsonArray(new String(response.body()), TestCaseResponse.class);
        CurrentUserResponse currentUser = scenarioContext.getContextValue("currentUser");

        SoftAssert asserts = new SoftAssert();
        asserts.assertTrue(!body.isEmpty(), "List should not be empty");
        asserts.assertTrue(body.stream().allMatch(testCaseResponse -> testCaseResponse.getOwnerId().equals(currentUser.getId())), "All test cases should belong to the current user");
        asserts.assertAll();
    }
}
