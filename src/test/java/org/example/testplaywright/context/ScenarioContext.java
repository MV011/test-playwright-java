package org.example.testplaywright.context;

import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ScenarioScope
public class ScenarioContext {
    private final Map<String, Object> context = new HashMap<>();

    public void setContextValue(String key, Object value) {
        context.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getContextValue(String key) {
        return (T) context.get(key);
    }

    public void removeContextValue(String key) {
        context.remove(key);
    }

    public void clearContext() {
        context.clear();
    }

    public boolean hasContextValue(String key) {
        return context.containsKey(key);
    }
}