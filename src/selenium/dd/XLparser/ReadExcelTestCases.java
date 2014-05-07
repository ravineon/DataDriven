package selenium.dd.XLparser;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @version 1.0
 * @author Ashvini Sharma
 *
 *This class is used to read XL file.
 *This class will be used to read InputXL file and will pass contents to Write XL file class.
 *
 */

public class ReadExcelTestCases {
	
	//Global Variables
	ArrayList TestCases = new ArrayList();
	ArrayList RequiredTestCases = new ArrayList();
	
	//Logging details in log.property file
	static Logger log = Logger.getLogger(ReadExcelTestCases.class.getName());
	static final String LOG_PROPERTIES_FILE = "Conf/log.properties";
	
	/**
	 * This function will be used to Read complete XL file and return values in Array List.
	 * This function requires three parameters: Path of XL file, XL Sheet name and Release number.
	 * @return 
	 */
	public ArrayList readFullXL(String filePath, String sheet_name, ArrayList scenarios) {
		
		//Logging details
		PropertyConfigurator.configure("Configuration/log.properties");
		log.info("Reading XL file for all Test Cases...");
		//System.out.println("Release no." + release_num);
		int Rcol=0;
		
		//Start Reading XL file
		try {
			FileInputStream FSRead = new FileInputStream(filePath);
			Workbook WB = new HSSFWorkbook(FSRead);
			Sheet sh = WB.getSheet(sheet_name);
			int rows = sh.getPhysicalNumberOfRows();
			//System.out.println("No. of rows in Input XL file for Test Scenarios Sheet = " + rows);
			int cols = sh.getRow(0).getLastCellNum();
			//System.out.println("No. of columns in input file for Test Scenarios Sheet = " + cols);
			//Debug: System.out.println("Release Number = " + releasenum);
			
			//Iterate Rows and Read complete input XL file
			for(int i=0; i<rows; i++) {
				TestCases.add(new ArrayList());				
				//Iterate Columns
				for(int j=0; j<cols; j++) {
					
						//Get Current Row
						Row current_row = sh.getRow(i);
						
						//To get date from excel file
						Cell cell = current_row.getCell(j);
						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					
						
						//Debug: System.out.println("row and col value =" + i + " , " + j);
						Cell blank_cell = current_row.getCell(j, current_row.CREATE_NULL_AS_BLANK);
						//Debug: System.out.println("Blank cell = " + blank_cell);
						int type = current_row.getCell(j).getCellType();
						//Debug: System.out.println("Value of Type = " + type);
						if(type == 3){
							//System.out.println("Empty Cell");
							((ArrayList)TestCases.get(i)).add("");
						}
						if (type == 1) {
							String data = current_row.getCell(j).getStringCellValue();
							//Debug: System.out.println("Cell Value" + " " + data + "\n");
							((ArrayList)TestCases.get(i)).add(data);
						}
						if (type == 0 ) {
							if((DateUtil.isCellDateFormatted(cell))){
								String Dvalue = sdf.format(current_row.getCell(j).getDateCellValue());
								((ArrayList)TestCases.get(i)).add(Dvalue);
							} else {
							double value = current_row.getCell(j).getNumericCellValue();
							//Debug: System.out.println("Cell Value" + " " + value + "\n");
							((ArrayList)TestCases.get(i)).add(value);
							}
						}
						type=-1;
	
				}
			}
			
			//Filter Required Scenarios from Test Case List
			String data=null;
			for(int r=0; r<TestCases.size(); r++){
				//Console Formatting:
				//String newLine = System.getProperty("line.separator");
				//System.out.println(newLine);
				for(int c=0; c <((ArrayList)TestCases.get(r)).size(); c++) {
					
					if (c == 0 ){
						data=((String)((ArrayList)TestCases.get(r)).get(c));
						for (int i=0; i<scenarios.size();i++) {
							if(data.equals(scenarios.get(i))) {
								//Debug: System.out.println(((ArrayList)TestCases.get(r)).get(c) +"  "); 
								//Debug: System.out.println(scenarios.get(i)); 
								String tcdata = (String) ((ArrayList)TestCases.get(r)).get(c+2);
								if(tcdata.equals("Y")){
									// Debug: System.out.println("Copy " + ((ArrayList)TestCases.get(r)).get(c+1));
									RequiredTestCases.add(TestCases.get(r));
									RequiredTestCases.add(new ArrayList());	
								}
							} else {	
								
							}
						} // End Inner IF
					} //End Outer IF
					
				} // End Inner For 
			} //End Outer For
			
			//Debug: System.out.println("This is required" + RequiredTestCases);
			
			FSRead.close();
			}catch (IOException e) {
				log.debug("Error in ReadExcel.java while reading XL file", e);
			}
			
			 //Debug: 
			/*System.out.println(" In Loop");
				for(int r=0; r<RequiredTestCases.size(); r++){
						String newLine = System.getProperty("line.separator");
						System.out.println(newLine);
						for(int c=0; c <((ArrayList)RequiredTestCases.get(r)).size(); c++) {
							System.out.print(((ArrayList)RequiredTestCases.get(r)).get(c) +"  ");  
						}
				} */  
			
				log.info("Reading XL Complete for Test Case...");			
				return RequiredTestCases;
	}
	
	
}
