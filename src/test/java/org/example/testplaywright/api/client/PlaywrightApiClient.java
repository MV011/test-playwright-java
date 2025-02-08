package org.example.testplaywright.api.client;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ScenarioScope
@Slf4j
public class PlaywrightApiClient {
    private final Playwright playwright;
    private APIRequestContext apiRequestContext;
    @Getter
    private final Map<String, String> headers = new java.util.HashMap<>();

    public PlaywrightApiClient() {
        this.playwright = Playwright.create();
    }

    public void setApiRequestContext(String baseUrl, Map<String, String> headers, double timeout) {

        log.debug("Initializing APIRequestContext with baseUrl: {}, headers: {}, timeout: {}", baseUrl, headers, timeout);

        if (apiRequestContext != null) {
            apiRequestContext.dispose();
        }

        apiRequestContext = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL(baseUrl)
                        .setExtraHTTPHeaders(headers)
                        .setTimeout(timeout)

        );
    }

    public APIResponse sendRequest(String method, String endpoint, RequestOptions options) {

        log.debug("Sending request to endpoint: {}", endpoint);
        setRequestHeaders(options);

        if (apiRequestContext == null) {
            throw new IllegalStateException("APIRequestContext not initialized. Call setApiRequestContext first.");
        }

        return switch (method.toUpperCase()) {
            case "GET" -> apiRequestContext.get(endpoint, options);
            case "POST" -> apiRequestContext.post(endpoint, options);
            case "PUT" -> apiRequestContext.put(endpoint, options);
            case "PATCH" -> apiRequestContext.patch(endpoint, options);
            case "DELETE" -> apiRequestContext.delete(endpoint, options);
            default -> throw new IllegalArgumentException("Invalid HTTP method: " + method);
        };
    }

    public APIResponse sendRequest(String method, String endpoint) {
        return sendRequest(method, endpoint, null);
    }

    public void addDefaultHeader(String key, String value) {
        headers.put(key, value);
    }

    @PreDestroy
    public void cleanup() {
        if (apiRequestContext != null) {
            apiRequestContext.dispose();
            apiRequestContext = null;
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    private void setRequestHeaders(RequestOptions options) {
        for (Map.Entry<String, String> header : headers.entrySet()) {
            options.setHeader(
                    header.getKey(),
                    header.getValue());
        }
    }
}
