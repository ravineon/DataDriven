package selenium.dd.XLparser;

import java.util.ArrayList;
import org.apache.log4j.Logger;

public class FilterTestCases {
	/**
	 * This class is used to filter the test cases from AraryList.
	 * 
	 * 
	 */
	
	//Global Variable
	ArrayList<String> scenario = new ArrayList<String>();
	
	//Logging details in log.property file
	static Logger log = Logger.getLogger(ReadExcelTestScenarios.class.getName());
	static final String LOG_PROPERTIES_FILE = "Conf/log.properties";
	
	public ArrayList<String> FilterCases(ArrayList FilterTestScenario){
		

		//Debug: System.out.println("Reading Filter Test Scenarios" + FilterTestScenario);
		for(int i=0; i<FilterTestScenario.size(); i++){
			for(int j=0;j<((ArrayList)FilterTestScenario.get(i)).size(); j++) {
				//Debug System.out.println(((ArrayList)FilterTestScenario.get(i)).get(j));
				if(j==0){
				String data = (String) ((ArrayList)FilterTestScenario.get(i)).get(j+1);
					if(data.equals("Y")){
					scenario.add((String) ((ArrayList)FilterTestScenario.get(i)).get(j));
					}
				}
			}
		}
		//Debug: 
		System.out.println("Scenarios to Execute : " + scenario);
		return scenario;
		}
}
