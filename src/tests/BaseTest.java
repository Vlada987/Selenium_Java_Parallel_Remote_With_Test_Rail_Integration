package tests;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import pages.HomePage;

public class BaseTest {

    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    public WebDriver getDriver() {
        return driver.get();
    }
    
    
    
    @Parameters({"browser","os"})
    @BeforeTest
    public void setUp(String browser,String os) throws MalformedURLException {
    	    	
        WebDriver ldriver = SetupDriver.remoteForParallel(browser,os);
        driver.set(ldriver);
    }

    @AfterTest
    public void tearDown() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
