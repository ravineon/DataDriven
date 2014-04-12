/**
 * 
 */
package selenium.dd.FunctionalCode;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.dd.PageObject.HomePageUrl;
import selenium.dd.PageObject.ClickLogin;


/**
 * @author Ashvini.Sharma
 *
 */
public class ExecuteTestCase {
	WebDriver driver = new FirefoxDriver();
	HomePageUrl HP = new HomePageUrl(driver); //Home page initiate with default URL
	ClickLogin id= new ClickLogin(); //SignIn Page Element to call locators
	
	ExecuteTestCase() {
		
	}
	
	@Test
	public void LogTest() {

		//Call Web element locator (Click) for SignIn Page
		WebElement SignInLoc=id.SignInPage(driver,"SignInButton");
		SignInLoc.click();
		
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("amazon");
            }
        }); 
		
		//Call Web element locator for Email ID
		WebElement UsrIDLoc=id.SignInPage(driver,"UserId");
		UsrIDLoc.sendKeys("ashv@live.in");

		//Call Web element locator for PassWord
		WebElement PassIDLoc=id.SignInPage(driver,"Password");
		PassIDLoc.sendKeys("intmain!1");
		
		//Call Web element locator (Click) for SignIn Button
		WebElement SignInButton =id.SignInPage(driver,"SubmitButton");
		SignInButton.click();
		
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("amazon.in");
            }
        }); 
		
        System.out.println("Page title is: " + driver.getTitle());         
        //Close the browser
        driver.quit();
	}

}
