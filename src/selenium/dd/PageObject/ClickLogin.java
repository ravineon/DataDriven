package selenium.dd.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClickLogin {

	public WebElement SignInPage(WebDriver driver, String location) {
		WebElement Locator=null;
		if(location.equals("SignInButton")) {
			Locator = driver.findElement(By.id("nav-signin-title"));
		}

		if(location.equals("UserId")) {
			Locator = driver.findElement(By.id("ap_email"));
		}
		
		if(location.equals("Password")) {
			Locator = driver.findElement(By.id("ap_password"));
		}
		
		if(location.equals("SubmitButton")) {
			Locator = driver.findElement(By.id("signInSubmit-input"));
		}
		return Locator;
	}
}
