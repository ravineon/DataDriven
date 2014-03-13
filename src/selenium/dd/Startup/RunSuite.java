package selenium.dd.Startup;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import selenium.dd.Configuration.Config;
import selenium.dd.Configuration.Configuration_Interface;
import selenium.dd.XLparser.ReadExcelTestCases;
import selenium.dd.XLparser.ReadExcelTestScenarios;
import selenium.dd.XLparser.FilterTestCases;
import selenium.dd.XLparser.WriteXLExecutablTestCases;

public class RunSuite {
	
	//Logging details in log.property file
	static Logger log = Logger.getLogger(RunSuite.class.getName());
	//static final String LOG_PROPERTIES_FILE = "Conf/log.properties";
	
	public static void main(String[] args) {
		
		/**
		 * 
		 *  @version 1.0
		 *  @author Ashvini Sharma
		 *  
		 *  This suite will do the following process. 
		 *  -Read configuration file
		 *  -Read XL file for scenarios 
		 *  -Read XL file for Test cases
		 *  -Create output XL file
		 *  -Test Functionality
		 *  -Update output XL file
		 *  -Create Report 
		 *  -Send Report 
		 *  
		*/
		
		//Logging details
		PropertyConfigurator.configure("Configuration/log.properties");
		
		// Reading configuration file
		log.info("Call to Read configuration file...");
		Configuration_Interface conf = new Config();
		String InputfilePath = conf.getInputfilePath();
		String OutputfilePath = conf.getoutputfilePath();
		String TS_sheet_name=conf.getInputTSSheetName();
		String TC_sheet_name=conf.getInputTCSheetName();
		String release=conf.getRelease();
		log.info("Call Read configuration file complete...");
		
		//Read Excel File for test scenarios and filter required release column and scenarios
		ArrayList ReadTestScenarios = new ArrayList();
		log.info("Call to Read XL file for scenarios...");
		ReadExcelTestScenarios TS_ReadExcel = new ReadExcelTestScenarios();
		ReadTestScenarios = TS_ReadExcel.readFullXL(InputfilePath, TS_sheet_name, release);
		log.info("Call Read XL file for scenarios complete...");
		//Debug: System.out.println(ReadTestScenarios);
		
		//Read Excel File for filtered Test Scenarios
		ArrayList FilterTestScenarios = new ArrayList();
		log.info("Call Read Test Scenarios from Array Object...");
		FilterTestCases FilterTC = new FilterTestCases();
		FilterTestScenarios = FilterTC.FilterCases(ReadTestScenarios);
		log.info("Call Read Test Scenarios filtered...");
		
		//Read Excel File for test cases and filter required scenarios columns
		ArrayList ReadTestCases = new ArrayList();
		log.info("Call to Read XL file for Test cases...");
		ReadExcelTestCases TS_ReadExcelTC = new ReadExcelTestCases();
		ReadTestCases = TS_ReadExcelTC.readFullXL(InputfilePath, TC_sheet_name, FilterTestScenarios);
		log.info("Call Reading XL file for Test cases complete...");
		
		//Create Output XL file for executable test cases and scenarios
		log.info("Call to Write XL file for creating output file...");
		WriteXLExecutablTestCases ExecutableTC = new WriteXLExecutablTestCases();
		ExecutableTC.ExecutableTestCases(ReadTestCases, OutputfilePath);
		log.info("Call to Write XL file for creating output file Complete ...");
		
		
		
	}

}
