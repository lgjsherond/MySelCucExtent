package com.demo.stepdefinitions;

import io.cucumber.java.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class googleDef {
    private WebDriver driver;
    Scenario scenario;
    String screenshotdir = System.getProperty("user.dir") + "/target/Screenshots";

    @Given("Launch JacPLUS home page")
    public void launchGooglePage() {
        WebDriverManager.chromedriver().setup();
//        ChromeOptions opt=new ChromeOptions();
//        opt.addArguments("--headless");
//        driver=new ChromeDriver(opt);
        driver=new ChromeDriver();
        driver.get("https://www.jacplus.com.au/");
        driver.manage().window().maximize();
        Assert.assertEquals(driver.getTitle(),"JacarandaPLUS");
    }

    @Before
    public void beforMethodSetUp(Scenario scenario) {

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            scenario.log("Scenario failure");
        }
        this.scenario = scenario;
//        if ((new File(screenshotdir)).exists())
//            FileUtils.cleanDirectory(new File(screenshotdir));
    }

    @AfterStep()
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
//        scenario.attach(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES),"image/png","");
    }

    @And("enter credentials")
    public void enterCredentials() {
        driver.findElement(By.name("username")).sendKeys("github@demo.com");
        driver.findElement(By.name("username")).sendKeys(Keys.ENTER);
        WebDriverWait wait=new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
        driver.findElement(By.name("password")).sendKeys("password");
    }

    @And("modify the cookie {string}")
    public void modifyTheCookie(String string) {
        Cookie cookie1=driver.manage().getCookieNamed("JACPLUS_SESSION");
        scenario.log("Current Cookie value is :"+cookie1+"\n");
        driver.manage().deleteCookieNamed("JACPLUS_SESSION");
        driver.manage().addCookie(new Cookie("JACPLUS_SESSION","jacplus.tc8_"+string));
        Cookie cookie2=driver.manage().getCookieNamed("JACPLUS_SESSION");
        scenario.log("New cookie value :"+cookie2+"\n");

    }

    @And("reload the page")
    public void reloadThePage() {
        driver.navigate().refresh();
        Cookie cookie3=driver.manage().getCookieNamed("JACPLUS_SESSION");
        scenario.log("Verify cookie value :"+cookie3+"\n");
    }

    @And("waiting")
    public void waiting() {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


    @Then("click back to Bookshelf")
    public void clickBackToBookshelf() {
        driver.navigate().back();
//        driver.findElement(By.partialLinkText("btnEbookGoBackToBookShelf")).click();
//        driver.findElement(By.xpath("///*[@id=\"usermenu\"]")).click();
//        driver.findElement(By.xpath("///*[@id=\"btnEbookGoBackToBookShelf\"]")).click();
        Assert.assertEquals(driver.getTitle(),"JacPLUS - Your bookshelf");
    }

    @Then("Click on AssessON")
    public void clickOnAssessON() {

    }

    @Then("click on eBook title {string}")
    public void clickOnEBookTitle(String ck) {
        driver.manage().deleteCookieNamed("CONTENT_JACPLUS_SESSION");
        driver.manage().addCookie(new Cookie("CONTENT_JACPLUS_SESSION","contentjacplus.tc_"+ck));
        WebElement ebuide=driver.findElement(By.linkText("Active Outcomes 1 2E PDHPE Stage 4"));
        if(ebuide.isDisplayed()){
            ebuide.click();
            Assert.assertEquals(driver.getTitle(),"Active Outcomes 1 2E PDHPE Stage 4 eGuidePLUS EPUR");
        }
        Cookie eck=driver.manage().getCookieNamed("CONTENT_JACPLUS_SESSION");
        scenario.log("My eBook Cookie value is :"+eck+"\n");

    }

    @Then("Click on AssessON {string}")
    public void clickOnAssessON(String ack) {
        driver.manage().deleteCookieNamed("ASSESSON_SESSION");
        driver.manage().addCookie(new Cookie("ASSESSON_SESSION","assesson.tc_"+ack));
        driver.findElement(By.linkText("assessON Core Science Stage 4 NSW Australian curriculum (CE)")).click();
        Assert.assertEquals(driver.getTitle(),"assessON - assessON Core Science Stage 4 NSW Australian curriculum (CE)");
        Cookie asck=driver.manage().getCookieNamed("ASSESSON_SESSION");
        scenario.log("My AssessON Cookie value is :"+asck+"\n");
    }

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
}
