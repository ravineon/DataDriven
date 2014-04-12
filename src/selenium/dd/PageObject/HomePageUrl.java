package selenium.dd.PageObject;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;

public class HomePageUrl {


	public  HomePageUrl (WebDriver driver) {
		driver.get("http://www.amazon.in");
		if(!driver.getTitle().startsWith("Online Shopping in India:")) {
			throw new NotFoundException("Page Not Found");
		}
	}
}
