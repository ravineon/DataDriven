package selenium.dd.XLparser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 
 * @version 1.0
 * @author Ashvini Sharma
 *
 *This class is used to write XL file.
 *
 *
 */


public class WriteXLExecutablTestCases {
	
	
	//Logging details in log.property file
	static Logger log = Logger.getLogger(ReadExcelTestScenarios.class.getName());

	public void ExecutableTestCases(ArrayList ExecutableTC, String OutputfilePath) {
	//Debug: System.out.println("Test Case to Execute" + ExecutableTC);
	//Logging details
		PropertyConfigurator.configure("Configuration/log.properties");	
		
		try {
			HSSFWorkbook WBwrite = new HSSFWorkbook();
			Sheet SHwrite = WBwrite.createSheet("Executable TestCases");
			Row rownum = null;
			
				for(int r=0; r<ExecutableTC.size(); r++){
					//String newLine = System.getProperty("line.separator");
					//System.out.println(newLine);
					rownum = SHwrite.createRow(r);
					rownum = SHwrite.getRow(r);
					
						for(int c=0; c <((ArrayList)ExecutableTC.get(r)).size(); c++) {
							Cell cell = rownum.createCell(c);
							String wdata = (String) ((ArrayList)ExecutableTC.get(r)).get(c);
							//Debug: System.out.println("Inside:");
							//Debug: System.out.print(((ArrayList)ExecutableTC.get(r)).get(c) +"  ");  
							cell.setCellValue(wdata);
						}
				} 
			
			
			FileOutputStream Fwrite = new FileOutputStream(new File(OutputfilePath));
			WBwrite.write(Fwrite);
			Fwrite.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		
	}
	
}
