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
import org.testng.Assert;

import java.io.File;

public class googleDef {
    private WebDriver driver;
    Scenario scenario;
    String screenshotdir = System.getProperty("user.dir") + "/target/Screenshots";

//    public String getBase64Screenshot(WebDriver driver) throws IOException {
//        String Base64StringofScreenshot="";
//        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//        // for saving screenshots in local - this is optional
//        Date oDate = new Date();
//        SimpleDateFormat oSDF = new SimpleDateFormat("ddMMYYYY_HHmmss");
//        String sDate = oSDF.format(oDate);
//        FileUtils.copyFile(src, new File(screenshotdir + "Screenshot_" + sDate + ".png"));
//        //
//        byte[] fileContent = FileUtils.readFileToByteArray(src);
//        Base64StringofScreenshot = "data:image/png;base64," + Base64.getEncoder().encodeToString(fileContent);
//        return Base64StringofScreenshot;
//    }

    @Given("Launch google page")
    public void launchGooglePage() {
        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
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
    public void attach_screenshot() throws Throwable {
        if(scenario.isFailed()){
            TakesScreenshot ts=(TakesScreenshot)driver;
            byte[] screenshot=ts.getScreenshotAs(OutputType.BYTES);
//            scenario.log("This is my failure message");
            scenario.attach(screenshot,"image/png","");
        }

//        ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(getBase64Screenshot(driver)); //for html
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @Then("verify the search page")
    public void verifyTheHomePage() {
        driver.findElement(By.name("q")).sendKeys("Sheron");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        Assert.assertEquals(driver.getTitle(),"Sheron - Gogle Search");
//        scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");

    }
}
