package org.example.testplaywright.runner;

import io.cucumber.spring.CucumberContextConfiguration;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.example.testplaywright.config.TestConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"org.example.testplaywright.steps",
                "org.example.testplaywright.hooks",
                "org.example.testplaywright.config.TestConfig"},
        plugin = {
                "pretty"
        },
        tags = "not @Ignore"
)
@CucumberContextConfiguration
@SpringBootTest(classes = TestConfig.class)
public class TestRunner extends AbstractTestNGCucumberTests {
    @DataProvider(parallel = true)
    @Override
    public Object[][] scenarios() {
        return super.scenarios();
    }
}