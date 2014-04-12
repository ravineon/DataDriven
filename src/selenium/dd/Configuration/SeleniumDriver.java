package selenium.dd.Configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

//Read config file
import selenium.dd.Configuration.Config;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * @version 1.0
 * @author: Ashvini Sharma
 * 
 * User can change driver from chrome to firefox or safari as required.
 * There is no need to change driver everywhere in code.
 * 
 * This file will be imported in FunctionalCode package.
*/

public class SeleniumDriver {
	
	//Logging details in log.property file
	static Logger log = Logger.getLogger(Config.class.getName());
	//static final String LOG_PROPERTIES_FILE = "Conf/log.properties";
	
	/* public SeleniumDriver() {
		PropertyConfigurator.configure("Conf/log.properties");
	} */
	
	public WebDriver getDriver(String ConfDriver) {
		//Logging details
		PropertyConfigurator.configure("Configuration/log.properties");
		log.info("Pick Driver to use.");
		
		Config readDriver = new Config();
		//String Driver = readDriver.DriverToUse();
		WebDriver driverToUse = null;
			if(ConfDriver.equals("chrome")) {
				driverToUse = new ChromeDriver();
				//TODO need to place OS Validation as well
				//MAC
				System.setProperty("webdriver.chrome.driver", "/Users/ashv/Automation/Selenium/DataDriven/BrowserNativeDriver/chromedriver");
				//WIN
				//System.setProperty("webdriver.chrome.driver", "/Users/ashv/Automation/Selenium/DataDriven/BrowserNativeDriver/chromedriver");
			}
			else if(ConfDriver.equals("firefox")) {
				driverToUse = new FirefoxDriver();
			}
			//TODO implement validation on Driver name
			return driverToUse;
	}
}
