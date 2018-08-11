package mobapp.pam;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Author: Pramod Patil
 * Description: This class contains the all the mobile app test cases to be executed 
 *
 */
public class App 
{
	public WebDriver driver;
	HashMap propertyData;
	/***
	 * To initialize the android mob app capabilities
	 * PlatformName, DeviceName, App
	 * 
	 */
	
	@BeforeTest
	public void testSetup()
	{
		
		System.out.println("Device capabilty setting started...");
		try {
			
			propertyData=CommonUtility.readPropertyFile("\\Resources\\Settings.properties");
			File appdir = new File(propertyData.get("Android_SDK_Path").toString());
			File app = new File(appdir, propertyData.get("APK_NAME").toString());
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("platformName", propertyData.get("Platform_Name"));
			capabilities.setCapability("deviceName", propertyData.get("Device_Name"));
			capabilities.setCapability("newCommandTimeout", propertyData.get("New_Command_Timeout"));
			capabilities.setCapability("app", app.getAbsolutePath());
			driver = new RemoteWebDriver(new URL(propertyData.get("Hub_Url").toString()), capabilities);
			Thread.sleep(30);
			System.out.println("Device capabilites set successfully and App launched successfully.");
			
		} catch (Exception e) {
			System.out.println("Class ActionKeywords | Method launchNativeMobileApp | Exception desc : " + e.getMessage());

		}
		
	}
	
	/***
	 * Test case to Buy product from the ebay app
	 * login-->Search-->Buy
	 */
    @Test
    public void testLoginAndBuyItem()
    {
    	System.out.println("Navigate to login screen");
    	CommonUtility.waitForElement(driver, "id", propertyData.get("leftpanel").toString());
    	CommonUtility.click(driver, "id", propertyData.get("leftpanel").toString());
    	
    	CommonUtility.waitForElement(driver, "id", propertyData.get("signin").toString());
    	CommonUtility.click(driver, "id", propertyData.get("signin").toString());

    	CommonUtility.waitForElement(driver, "id", propertyData.get("username").toString());
    	CommonUtility.enterValue(driver, "id", propertyData.get("username").toString(),"Pramod");
    	CommonUtility.enterValue(driver, "id", propertyData.get("username").toString(),"pwd");
    	CommonUtility.click(driver, "id", propertyData.get("signin").toString());

    	CommonUtility.waitForElement(driver, "id", propertyData.get("search").toString());
    	System.out.println("Login Successfully.");

    	CommonUtility.enterValue(driver, "id", propertyData.get("search").toString(),"tshirts");
    	CommonUtility.click(driver, "id", propertyData.get("search").toString());
    	
    	CommonUtility.click(driver, "id", propertyData.get("firstseacheditem").toString());
    	System.out.println("Item Selected.");

    	CommonUtility.click(driver, "id", propertyData.get("buyitnow").toString());
    	CommonUtility.click(driver, "id", propertyData.get("selectsize").toString());
    	System.out.println("Item size Selected.");
    	
    	CommonUtility.waitForElement(driver, "id", propertyData.get("buyitnow").toString());
    	CommonUtility.click(driver, "id", propertyData.get("buyitnow").toString());
    	System.out.println("Clicked on buy button.");
    }   
    
   @AfterTest
   public void tearDown()
   {
	   driver.close();
	   driver.quit();
	   System.out.println("Stopped");
   }
}
