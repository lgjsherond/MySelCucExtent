package com.demo.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;

public class googleDef {
    private WebDriver driver;
    Scenario scenario;
    String screenshotdir = System.getProperty("user.dir") + "/target/Screenshots";

    @Given("Launch JacPLUS home page")
    public void launchGooglePage() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opt=new ChromeOptions();
        opt.addArguments("--headless");
        driver=new ChromeDriver(opt);
        driver.get("https://www.jacplus.com.au/");
        driver.manage().window().maximize();
        Assert.assertEquals(driver.getTitle(),"JacarandaPLUS");
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

    @Then("verify the dashboard")
    public void verifyTheHomePage() {
        driver.findElement(By.name("password")).sendKeys(Keys.ENTER);
        Assert.assertEquals(driver.getTitle(),"JacPLUS - Your bookshelf");
        Cookie cookie=driver.manage().getCookieNamed("JACPLUS_SESSION");
        scenario.log("Cookie value is :"+cookie+"\n");
        System.out.println("My current server  is "+cookie);
    }

    @And("enter credentials")
    public void enterCredentials() {
        driver.findElement(By.name("username")).sendKeys("prodtest@wiley.lk");
        driver.findElement(By.name("username")).sendKeys(Keys.ENTER);
        WebDriverWait wait=new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
        driver.findElement(By.name("password")).sendKeys("password");
    }

    @And("modify the cookie")
    public void modifyTheCookie() {
        Cookie cookie1=driver.manage().getCookieNamed("JACPLUS_SESSION");
        scenario.log("Current Cookie value is :"+cookie1+"\n");
        driver.manage().deleteCookieNamed("JACPLUS_SESSION");
        driver.manage().addCookie(new Cookie("JACPLUS_SESSION","jacplus.tc8_syd-as-x5"));
        Cookie cookie2=driver.manage().getCookieNamed("JACPLUS_SESSION");
        scenario.log("New cookie value :"+cookie2+"\n");

    }

    @And("reload the page")
    public void reloadThePage() {
        driver.navigate().refresh();
        Cookie cookie3=driver.manage().getCookieNamed("JACPLUS_SESSION");
        scenario.log("Verify cookie value :"+cookie3+"\n");

    }
}
