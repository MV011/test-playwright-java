package org.example.testplaywright.steps;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.FormData;
import com.microsoft.playwright.options.RequestOptions;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.spring.ScenarioScope;
import org.example.testplaywright.api.client.PlaywrightApiClient;
import org.example.testplaywright.context.ScenarioContext;
import org.example.testplaywright.models.request.CreateUserRequest;
import org.example.testplaywright.models.response.LoginResponse;
import org.example.testplaywright.models.response.UserResponse;
import org.example.testplaywright.utils.ApiUtils;
import org.example.testplaywright.utils.CucumberUtils;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@ScenarioScope
public class ApiAuthSteps {

    private final PlaywrightApiClient playwrightApiClient;
    private final ScenarioContext scenarioContext;

    public ApiAuthSteps(PlaywrightApiClient playwrightApiClient, ScenarioContext scenarioContext) {
        this.playwrightApiClient = playwrightApiClient;
        this.scenarioContext = scenarioContext;
    }

    @Given("^I have a payload set for CreateUserRequest with the following data:$")
    public void iHaveAPayloadSetForCreateUserRequestWithTheFollowingData(DataTable table) {
        List<Map<String, String>> data = CucumberUtils.processDataTable(table);

        CreateUserRequest request = CreateUserRequest.builder().
                email(data.getFirst().get("email")).
                password(data.getFirst().get("password")).
                username(data.getFirst().get("username")).build();

        scenarioContext.setContextValue("payload", request);
    }

    @And("the response should contain user information")
    public void theResponseShouldContainUserInformation() {
        APIResponse response = scenarioContext.getContextValue("response");
        CreateUserRequest request = scenarioContext.getContextValue("payload");
        UserResponse body = ApiUtils.convertResponseBody(response, UserResponse.class);

        SoftAssert asserts = new SoftAssert();
        asserts.assertEquals(body.getEmail(), request.getEmail(), "Email should be equal");
        asserts.assertEquals(body.getUsername(), request.getUsername(), "Username should be equal");
        asserts.assertEquals(body.getIsActive().booleanValue(), true, "User is not active");
        asserts.assertEquals(body.getId() > 0, true, "User id should be greater than 0");
        asserts.assertAll();
    }

    @Given("^I have request options set for login with the following data:$")
    public void iHaveAPayloadSetForLoginWithTheFollowingData(DataTable table) {
        List<Map<String, String>> data = CucumberUtils.processDataTable(table);

        RequestOptions requestOptions = RequestOptions.create();

        requestOptions.setForm(FormData.create().
                set("username", data.getFirst().get("username")).
                set("password", data.getFirst().get("password")));

        scenarioContext.setContextValue("requestOptions", requestOptions);
    }

    @And("the response should contain a token")
    public void theResponseShouldContainAToken() {
        APIResponse response = scenarioContext.getContextValue("response");
        LoginResponse body = ApiUtils.convertResponseBody(response, LoginResponse.class);

        assertFalse(body.getAccessToken().isEmpty(), "Token should not be empty");

        scenarioContext.setContextValue("token", body.getAccessToken());
    }

    @And("I update my headers with the authorization token")
    public void iUpdateMyHeadersWithTheAuthorizationToken() {
        String token = scenarioContext.getContextValue("token");
        playwrightApiClient.addDefaultHeader("Authorization", "Bearer " + token);
    }
}
