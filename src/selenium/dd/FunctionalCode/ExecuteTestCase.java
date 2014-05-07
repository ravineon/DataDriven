/**
 * 
 * Read Output file
 * Run loop and execute one by one test case
 * 
 */
package selenium.dd.FunctionalCode;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.dd.PageObject.LocHomePageUrl;
import selenium.dd.PageObject.LocClickLogin;

/**
 * @author Ashvini.Sharma
 * 
 */

public class ExecuteTestCase {

	// Global Variables
	// Logging details in log.property file
	static Logger log = Logger.getLogger(ExecuteTestCase.class.getName());

	// static final String LOG_PROPERTIES_FILE = "Configuration/log.properties";
	ArrayList TestScenarios = new ArrayList();

	// This will dynamic from parser class
	//Private: Only one constructor through class will initiated  
	private static WebDriver driver = new FirefoxDriver();

	/* Temp Debug
	public static void main(String[] args) {
		ReadTestCase();
	}*/

	// Call Parser so that got output file

	// This will dynamic from parser class
	static String OutputfilePath = "F:\\Automation\\Selenuim\\DataDriven\\Output\\Output.xls";
	// This will be constant value
	static String sheet_name = "Executable TestCases";


	// Locators Initiation from Page Object
	// HomePageUrl HP = new HomePageUrl(driver); //Home page initiate with
	// default URL
	// ClickLogin id= new ClickLogin(); //SignIn Page Element to call locators

	@Test
	// Read XL file have Executable content
	public void ReadTestCase() {
		
		boolean flag=false;
		PropertyConfigurator.configure("Configuration/log.properties");
		log.info("Reading Output XL file to Execute test Cases...");

		// Start Reading XL file
		try {
			FileInputStream FSRead = new FileInputStream(OutputfilePath);
			Workbook WB = new HSSFWorkbook(FSRead);
			Sheet sh = WB.getSheet(sheet_name);
			int rows = sh.getPhysicalNumberOfRows();
			// System.out.println("No. of rows in Input XL file for Test Scenarios Sheet = "
			// + rows);
			int cols = sh.getRow(0).getLastCellNum();
			// System.out.println("No. of columns in input file for Test Scenarios Sheet = "
			// + cols);
			int ExecuteTCCounter = -1;

			// Iterate Rows and Read complete input XL file
			for (int i = 0; i < rows; i++) {
				ExecuteTCCounter++;
				TestScenarios.add(new ArrayList());
				// Iterate Columns
				innerLoop: for (int j = 0; j < cols; j++) {
					Row current_row = sh.getRow(i);
					// Debug: System.out.println("row and col value =" + i + " , " + j);
					Cell blank_cell = current_row.getCell(j,current_row.CREATE_NULL_AS_BLANK);
					// Debug: System.out.println("Blank cell = " + blank_cell);
					int type = current_row.getCell(j).getCellType();
					// Debug: System.out.println("Value of Type = " + type);
					if (type == 3 && j == 0) {
						ExecuteTCCounter--;
						TestScenarios.remove(new ArrayList());
						break innerLoop;
					}

					if (type == 3) {
						// System.out.println("Empty Cell");
						((ArrayList) TestScenarios.get(ExecuteTCCounter)).add("");
					}
					if (type == 1) {
						String data = current_row.getCell(j).getStringCellValue();
						// System.out.println("Cell Value" + " " + data + "\n");
						((ArrayList) TestScenarios.get(ExecuteTCCounter)).add(data);
					}
					if (type == 0) {
						double value = current_row.getCell(j).getNumericCellValue();
						// System.out.println("Cell Value" + " " + value + "\n");
						((ArrayList) TestScenarios.get(ExecuteTCCounter)).add(value);
					}
					type = -1;
				}
			}   FSRead.close();
		} catch (IOException e) {
			log.debug("Error in ReadExcel.java while reading XL file", e);
		}

		// Reading Complete Array (Stored output XL file) to execute Test Case
		log.info("Start Reading Test Cases to Execute...");

		//Debug:
		//System.out.println("Test Scenario Size" + TestScenarios.size());
		//System.out.println("Test Scenario row size" + ((ArrayList) TestScenarios.get(0)).size());

		
		// for(int i=0; i<TestScenarios.size();i++) {
		for (int i = 0; i < 1; i++) {
			String newLine = System.getProperty("line.separator");
			System.out.println(newLine);

			log.info("Executing Test Case No." + i);
			next:
			for (int c = 0; c < ((ArrayList) TestScenarios.get(i)).size(); c++) {

				if (c == 0 || c == 1) {
					log.info("Executing " + (String) ((ArrayList) TestScenarios.get(i)).get(c));
				}
				if( ((String)((ArrayList) TestScenarios.get(i)).get(c)).equals("")) {
					//System.out.println("Bank");
					//Need to do some validation later: Check if user error and cell value is empty. (i.e. check next cell value).
					break next;
				}
				
				if (c > 2) {
					String MethodName = (String) ((ArrayList) TestScenarios.get(i)).get(c);
					//System.out.println("Ash " + MethodName);
					String className = "selenium.dd.FunctionalCode.ExecuteTestCase";
					
					//Get input if type defined in XL column
					if(MethodName.contains("Type")) {
						//Increment to get the next cell value and set flag
						c++; flag=true;
						
						//Checking if last column have type. This is validation for a defect cause user error in XL sheet
						if(c==((ArrayList) TestScenarios.get(i)).size()) {
							driver.quit();
							log.info("Test Case Execution finished, checking for next test case");
							break next;
						}
					}
					

					try {
						Class cls = Class.forName(className);
						Object obj = cls.newInstance();
						
						//Defined to parameterized reflection method call
						Class[] paramTypes = new Class[1];
						paramTypes[0]=String.class;
						
						//If type was defined in XL column get the next 'Input' value from XL
						//And invoke method that required parameters. + Reset flag to flase.
						if(flag) {
						flag=false;
						String Input = (String) ((ArrayList) TestScenarios.get(i)).get(c);
						Method method = cls.getMethod(MethodName, paramTypes);
						method.invoke(obj,Input);
						} // else invoke simple reflection method (i.e. without parameter). 
						else {
						Method method = cls.getMethod(MethodName);
						method.invoke(obj);
						}
					}

					catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						log.debug(e);
						// e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				//Checking for last column of row, so that we can close driver and close browser.
				//We will re initiate driver and re-open browser for next test case
				if (c == ((ArrayList) TestScenarios.get(i)).size()-1) {
					log.info("Closing Driver for " + i  + " Test Case");
					driver.quit();
				}
			
			} 	// Close inner For loop
		} // Close outer For loop
	}

	
	//All Locator functions are defined here
	//Function names are defined in Excel file as keyword.
	// **************************** // ********************************* // 
	public void HomePageUrl() {
		LocHomePageUrl HPUrl = new LocHomePageUrl(driver);
	}

	public void ClickLogin() {
		LocClickLogin LoginClick = new LocClickLogin();
		WebElement SignInLoc = LoginClick.SignInPage(driver, "SignInButton");
		SignInLoc.click();
		// WAIT
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith("amazon");
			}
		});
	}

	public void TypeUserID(String type) {
		LocClickLogin Id = new LocClickLogin();
		WebElement LocId = Id.SignInPage(driver, "UserId");
		LocId.sendKeys(type);
	}
	
	public void TypePass(String type) {
		LocClickLogin pass = new LocClickLogin();
		WebElement Locpass = pass.SignInPage(driver, "Password");
		Locpass.sendKeys(type);
	}
	
	public void submit() {
		LocClickLogin sub = new LocClickLogin();
		WebElement Locsub = sub.SignInPage(driver, "SubmitButton");
		Locsub.submit();
	}

}


