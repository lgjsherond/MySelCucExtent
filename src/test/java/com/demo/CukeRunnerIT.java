package com.demo;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        plugin = {"pretty",
                "html:target/cucumber-reports/report.html",
                "json:target/cucumber-reports/report.json",
//                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "timeline:target/timeline-thread/"
        },
        features = {"src/test/resources/Feature"},
        glue = {"com.demo.stepdefinitions"}
)

public class CukeRunnerIT extends AbstractTestNGCucumberTests{

        @Override
        @DataProvider(parallel = true)
        public Object[][] scenarios(){
                return  super.scenarios();
        }

}
