import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class reportDefinition {

long topPerformersThreshold;
boolean useExprienceMultiplier;
long periodLimit;
	
public reportDefinition (long topPerformersThreshold,boolean useExprienceMultiplier,long periodLimit) {
	this.topPerformersThreshold=topPerformersThreshold;
	this.useExprienceMultiplier=useExprienceMultiplier;
	this.periodLimit=periodLimit;
}

public reportDefinition(String path) {
		JSONParser parser = new JSONParser();
        JSONObject report = new JSONObject();
		try {
			report = (JSONObject) parser.parse(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
          long topPerformersThreshold = (long) report.get("topPerformersThreshold");

          boolean useExprienceMultiplier = (boolean) report.get("useExprienceMultiplier");
          
          long periodLimit = (long) report.get("periodLimit");
          
      	 this.topPerformersThreshold=topPerformersThreshold;
    	 this.useExprienceMultiplier=useExprienceMultiplier;
    	 this.periodLimit=periodLimit;
}

}
