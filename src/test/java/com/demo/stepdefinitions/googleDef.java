package com.demo.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

import java.io.File;

public class googleDef {
    private WebDriver driver;
    Scenario scenario;
    String screenshotdir = System.getProperty("user.dir") + "/target/Screenshots";

    @Given("Launch google page")
    public void launchGooglePage() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opt=new ChromeOptions();
        opt.addArguments("--headless");
        driver=new ChromeDriver(opt);
        driver.get("http://google.com");
        driver.manage().window().maximize();
        Assert.assertEquals(driver.getTitle(),"Google");
    }

    @Before
    public void beforMethodSetUp(Scenario scenario) throws Throwable {
        this.scenario = scenario;
        if ((new File(screenshotdir)).exists())
            FileUtils.cleanDirectory(new File(screenshotdir));
    }

    @AfterStep
    public void attach_screenshot() {
//        if(scenario.isFailed()){
            TakesScreenshot ts=(TakesScreenshot)driver;
            byte[] screenshot=ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png","");
//        }
    }

    @After
    public void tearDown(){
        if(driver!=null){
            driver.quit();
        }
    }

    @Then("verify the search page")
    public void verifyTheHomePage() {
        driver.findElement(By.name("q")).sendKeys("Sheron");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        Assert.assertEquals(driver.getTitle(),"Sheron - Google Search");
    }
}
