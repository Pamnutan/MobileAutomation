package mobapp.pam;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
/**
 *  Author: Pramod Patil
 *  Description: Contains common operations in this class
 *
 */
public class CommonUtility {
	
	/**
	 * wait for element to be visible on the screen
	 * @param driver - webdriver instance
	 * @param locator - type of locator
	 * @param value - locator value
	 */
	 public static void waitForElement(WebDriver driver, String locator, String value)
	    {
	    	WebDriverWait wait = new WebDriverWait(driver, 60);
	    	wait.until(ExpectedConditions.visibilityOf(findElement(driver,locator,value)));
	    }

	 /***
	  * 
	  * Common function to identify element on the page by its locator type and value
	  * 
	  * @param driver - webdriver instance
	  * @param locator - type of locator
	  * @param value - locator value	  
	  * @return if found then that webelement else returns null 
	  */
	 public static WebElement findElement(WebDriver driver, String locator, String Value)
	 {
		 WebElement element;
		 switch (locator) {
		 	case "id":
		 		element=driver.findElement(By.id(Value));
		 		break;
		 	case "name":
		 		element=driver.findElement(By.name(Value));
		 		break;
		 	case "xpath":
		 		element=driver.findElement(By.xpath(Value));
		 		break;
		 	case "classname":
		 		element=driver.findElement(By.className(Value));
		 		break;
		 	case "tagname":
		 		element=driver.findElement(By.tagName(Value));
		 		break;
		 	case "css":
		 		element=driver.findElement(By.cssSelector(Value));
		 		break;
		 	case "linktext":
		 		element=driver.findElement(By.linkText(Value));
		 		break;
		 	case "parial_linktext":
		 		element=driver.findElement(By.partialLinkText(Value));
		 		break;
		 	default:
		 		element=null;
		 		System.out.println("Element not found");
		 		Assert.fail("Element not found");
		 		break;
		}
		System.out.println("Element found");
		return element;
	 }

	 /**
	  * Perform click operation on the element
	  * @param driver - webdriver
	  * @param locator - type of element
	  * @param value - locator value
	  */
	public static void click(WebDriver driver, String locator, String value) {
		findElement(driver, locator, value).click();
		System.out.println("Perform clicked on element.");
	}
	

	 /**
	  * Perform clear  text operation on the element
	  * @param driver - webdriver
	  * @param locator - type of element
	  * @param value - locator value
	  */
	public static void clear(WebDriver driver, String locator, String value) {
		findElement(driver, locator, value).clear();
	}
	
	/**
	 * 
	 * To enter the value to given locator element
	 * @param driver -webdriver
	 * @param locator -type of locator
	 * @param locatorValue -locatorvalue
	 * @param enterVal -value to enter
	 */
	public static void enterValue(WebDriver driver, String locator, String locatorValue, String enterVal) {
		clear(driver, locator, locatorValue);
		findElement(driver, locator, locatorValue).sendKeys(enterVal);
		System.out.println("Entered value="+enterVal);
	}
	
	/**
	 * select from drop down
	 * @param driver
	 * @param parentLocator
	 * @param parentValue
	 * @param childLocator
	 * @param childValue
	 */
	public static void selectFromDropdown(WebDriver driver, String parentLocator, String parentValue,String childLocator, String childValue)
	{
		WebElement parentElement = findElement(driver, parentLocator,parentValue);
		WebElement childElement1 = findElement((WebDriver) parentElement, parentLocator,parentValue);
		childElement1.click();
	}
	
	
	/***
	 * Perform the .property file reading and store its contents in the hashmap through out the execution
	 * 
	 * @param filename : file path to read
	 * @return - file data hash ma
	 */
	public static HashMap<String, String> readPropertyFile(String filename) {
		HashMap<String, String> config = new HashMap<>();
		try {
			File file = new File(filename);

			if (file.exists()) {
				FileInputStream fileInput = new FileInputStream(file);
				Properties properties = new Properties();
				properties.load(fileInput);
				fileInput.close();
				Enumeration<Object> enuKeys = properties.keys();
				while (enuKeys.hasMoreElements()) {
					String key = (String) enuKeys.nextElement();
					String value = properties.getProperty(key);
					config.put(key, value);
				}
			} else {
				System.out.println("Exception desc : File not found at location="+ file.getAbsolutePath());
			}
		} catch (Exception e) {
			System.out.println("Exception desc : " + e.getMessage());		
		}
		return config;
	}
	
	
}
