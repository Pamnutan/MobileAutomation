package mobapp.pam;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
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

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

/**
 * Author: Pramod Patil Description: This class contains the all the mobile app
 * test cases to be executed
 *
 */
public class App {

	public WebDriver driver; // To maintain single webdrive instance
	HashMap propertyData; // To store the data values
	ExtentReports ext;
	ExtentTest test;

	/***
	 * To initialize the android mobile app capabilities PlatformName,
	 * DeviceName, App etc.
	 */
	@BeforeTest
	public void setup() {
		System.out.println("Device capabilty setting started...");
		try {

			propertyData = CommonUtility.readPropertyFile("\\Resources\\Settings.properties");
			File appdir = null;
			File app = null;

			ext = new ExtentReports("C:\\index.html");

			if (propertyData.get("Platform_Name").equals("android")) {
				// Capabilities For android application
				appdir = new File(propertyData.get("Android_SDK_Path").toString());
				app = new File(appdir, propertyData.get("APK_NAME").toString());
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability("platformName", propertyData.get("Platform_Name"));
				capabilities.setCapability("deviceName", propertyData.get("Device_Name"));
				capabilities.setCapability("appPackage", propertyData.get("appPackage"));
				capabilities.setCapability("appActivity", propertyData.get("appActivity"));
				capabilities.setCapability("newCommandTimeout", propertyData.get("New_Command_Timeout"));
				capabilities.setCapability("app", app.getAbsolutePath());
				driver = new AndroidDriver(new URL(propertyData.get("Hub_Url").toString()), capabilities);

			} else if (propertyData.get("Platform_Name").equals("ios")) {
				// Capabilities For ios application
				appdir = new File(propertyData.get("ios_SDK_Path").toString());
				app = new File(appdir, propertyData.get("ipa_NAME").toString());
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability("platformName", propertyData.get("Platform_Name"));
				capabilities.setCapability("deviceName", propertyData.get("Device_Name"));
				capabilities.setCapability("app", app.getAbsolutePath());
				driver = new IOSDriver(new URL(propertyData.get("Hub_Url").toString()), capabilities);
			} else {
				System.out.println("Platfrom not selected.");
				Assert.fail();
			}
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			System.out.println("Device capabilites set successfully and App launched successfully.");
		} catch (Exception e) {
			System.out.println("Class App | Method setup | Exception desc : " + e.getMessage());
			Assert.fail();
		}
	}

	/****
	 * Test case to Buy product from the flipcart app login-->Search-->Buy
	 */

	@Test
	public void testLoginAndBuyItemFlipcart() {
		test = ext.startTest("");
		test.log(LogStatus.INFO, "Flipcart login->add to cart flow");

		System.out.println("Open Flipcart app");
		CommonUtility.waitForElement(driver, "id", propertyData.get("btnleftmenu").toString());
		test.log(LogStatus.PASS, "Flipcart app launched successfully.");
		CommonUtility.click(driver, "id", propertyData.get("btnleftmenu").toString());

		CommonUtility.waitForElement(driver, "name", propertyData.get("btndrawer").toString());
		CommonUtility.click(driver, "name", propertyData.get("btndrawer").toString());

		CommonUtility.waitForElement(driver, "name", propertyData.get("menumyaccount").toString());
		CommonUtility.click(driver, "name", propertyData.get("menumyaccount").toString());

		test.log(LogStatus.PASS, "Navigated to login page.");

		System.out.println("Login to flipcart");
		CommonUtility.waitForElement(driver, "id", propertyData.get("username").toString());
		CommonUtility.enterValue(driver, "id", propertyData.get("username").toString(),
				propertyData.get("username_value").toString());

		CommonUtility.waitForElement(driver, "id", propertyData.get("password").toString());
		CommonUtility.enterValue(driver, "id", propertyData.get("password").toString(),
				propertyData.get("password_value").toString());

		CommonUtility.waitForElement(driver, "id", propertyData.get("btnlogin").toString());
		CommonUtility.click(driver, "id", propertyData.get("btnlogin").toString());

		System.out.println("Search item");
		CommonUtility.waitForElement(driver, "id", propertyData.get("search").toString());
		test.log(LogStatus.PASS, "Login successful.");
		CommonUtility.enterValue(driver, "id", propertyData.get("search").toString(),
				propertyData.get("search_value").toString());

		CommonUtility.waitForElement(driver, "name", propertyData.get("Search_select_value").toString());
		CommonUtility.click(driver, "name", propertyData.get("Search_select_value").toString());
		test.log(LogStatus.PASS, "Proecut search.");
		CommonUtility.waitForElement(driver, "xpath", propertyData.get("select_first_product").toString());
		CommonUtility.click(driver, "xpath", propertyData.get("select_first_product").toString());
		System.out.println("Add To cart");
		CommonUtility.waitForElement(driver, "name", propertyData.get("btnaddtocart").toString());
		CommonUtility.click(driver, "name", propertyData.get("btnaddtocart").toString());
		test.log(LogStatus.PASS, "Added to cart.");
		ext.endTest(test);

	}

	/***
	 * Test case to Buy product from the ebay app login-->Search-->Buy
	 */

	public void testLoginAndBuyItemEbay() {
		test = ext.startTest("");
		test.log(LogStatus.INFO, "Launch ebay app");

		/**
		 * To Do:- currently app not working
		 * 
		 */
		System.out.println("Navigate to login screen");
		CommonUtility.waitForElement(driver, "id", propertyData.get("leftpanel").toString());
		CommonUtility.click(driver, "id", propertyData.get("leftpanel").toString());

		CommonUtility.waitForElement(driver, "id", propertyData.get("signin").toString());
		CommonUtility.click(driver, "id", propertyData.get("signin").toString());

		CommonUtility.waitForElement(driver, "id", propertyData.get("username").toString());
		CommonUtility.enterValue(driver, "id", propertyData.get("username").toString(), "Pramod");
		CommonUtility.enterValue(driver, "id", propertyData.get("username").toString(), "pwd");
		CommonUtility.click(driver, "id", propertyData.get("signin").toString());

		CommonUtility.waitForElement(driver, "id", propertyData.get("search").toString());
		System.out.println("Login Successfully.");

		CommonUtility.enterValue(driver, "id", propertyData.get("search").toString(), "tshirts");
		CommonUtility.click(driver, "id", propertyData.get("search").toString());

		CommonUtility.click(driver, "id", propertyData.get("firstseacheditem").toString());
		System.out.println("Item Selected.");

		CommonUtility.click(driver, "id", propertyData.get("buyitnow").toString());

		WebElement parentElement1 = driver.findElement(By.id("size"));
		WebElement childElement1 = parentElement1.findElement(By.xpath("//android.view.View[@index='1']"));
		childElement1.click();
		System.out.println("First Item with index 1 is Selected.");
		// To do: Replace this with selectFromDropdown() method

		CommonUtility.waitForElement(driver, "id", propertyData.get("buyitnow").toString());
		CommonUtility.click(driver, "id", propertyData.get("buyitnow").toString());
		System.out.println("Clicked on buy button.");
		ext.endTest(test);
	}

	@AfterTest
	public void tearDown() {
		driver.close();
		driver.quit();
		ext.flush();
		System.out.println("Stopped");
	}
}
