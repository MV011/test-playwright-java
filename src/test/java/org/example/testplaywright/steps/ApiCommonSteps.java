package org.example.testplaywright.steps;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.ScenarioScope;
import org.example.testplaywright.api.client.PlaywrightApiClient;
import org.example.testplaywright.context.ScenarioContext;
import org.example.testplaywright.enums.Method;
import org.example.testplaywright.models.response.CurrentUserResponse;
import org.example.testplaywright.utils.ApiUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@ScenarioScope
public class ApiCommonSteps {

    @ParameterType("POST|GET|PUT|DELETE|PATCH")
    public Method method(String value) {
        return Method.valueOf(value);
    }

    private final PlaywrightApiClient playwrightApiClient;
    private final ScenarioContext scenarioContext;

    public ApiCommonSteps(PlaywrightApiClient playwrightApiClient, ScenarioContext scenarioContext) {
        this.playwrightApiClient = playwrightApiClient;
        this.scenarioContext = scenarioContext;
    }

    @When("I send a {method} request to {string}")
    public void sendRequestToEndpoint(Method method, String endpoint) {

        RequestOptions requestOptions = RequestOptions.create();
        if(scenarioContext.hasContextValue("requestOptions")) {
            requestOptions = scenarioContext.getContextValue("requestOptions");
        }
        else if(scenarioContext.hasContextValue("payload")) {
            requestOptions.setData(scenarioContext.<Object>getContextValue("payload"));
        }
        scenarioContext.setContextValue("response", playwrightApiClient.sendRequest(method.name(), endpoint, requestOptions));
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int code) {
        var response = scenarioContext.<APIResponse>getContextValue("response");

        assertEquals(response.status(), code, "Response status code should be " + code);
    }

    @Then("the response should contain the error {string}")
    public void theResponseShouldContainTheError(String message) {
        var response = scenarioContext.<APIResponse>getContextValue("response");
        var body = new String(response.body());

        assertTrue(body.contains(message), "Response should contain the error " + message);
    }

    @And("I remove any leftover request data")
    public void iRemoveAnyLeftoverRequestData() {
        if(scenarioContext.hasContextValue("requestOptions")) {
            scenarioContext.removeContextValue("requestOptions");
        }
        if(scenarioContext.hasContextValue("payload")) {
            scenarioContext.removeContextValue("payload");
        }
    }

    @And("I save the logged in user's data")
    public void iSaveTheLoggedInUserSData() {
        APIResponse response = scenarioContext.getContextValue("response");
        CurrentUserResponse body = ApiUtils.convertResponseBody(response, CurrentUserResponse.class);

        scenarioContext.setContextValue("currentUser", body);
    }
}
