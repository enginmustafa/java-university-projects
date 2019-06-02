import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class getData {
	
	ArrayList<reportedEmployee> checkedEmployees = new ArrayList<reportedEmployee>();
	
	public getData(String path, reportDefinition report) {

    JSONParser parser = new JSONParser();
	try {
	 JSONArray data = (JSONArray) parser.parse(new FileReader(path));
	 for (Object object : data)
        {
          JSONObject person = (JSONObject) object;
          
          String name = (String) person.get("name");

          long totalSales = (long) person.get("totalSales");
          
          long salesPeriod = (long) person.get("salesPeriod");
          
          double experienceMultiplier = (double) person.get("experienceMultiplier");
          
          Employee employee = new Employee(name,totalSales,salesPeriod,experienceMultiplier);
          checkEmployee(employee,report);		          
        }
	 //sort array list for further use(percentile rank)
	 sortArrayListByScore(checkedEmployees);

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
	}


	private void checkEmployee(Employee employee, reportDefinition report) {
		double score = getScore(report.useExprienceMultiplier,employee);
		
        reportedEmployee checkedEmployee = new reportedEmployee(employee.name,score);
        //have sales period that is equal or less than the periodLimit property;
        if(employee.salesPeriod<=report.periodLimit) {
		checkedEmployees.add(checkedEmployee);
        }
		
	}

	private double getScore(boolean uem, Employee e) {
		if (uem) {
			return e.totalSales/e.salesPeriod*e.experienceMultiplier;
		}
		else {
		    return e.totalSales/e.salesPeriod;
		}			
	}
	private void sortArrayListByScore(ArrayList<reportedEmployee> list) {
		Collections.sort(list, new Comparator<reportedEmployee>() {
		    @Override
		    public int compare(reportedEmployee re1, reportedEmployee re2) {
		        if (re1.score > re2.score)
		            return 1;
		        if (re1.score < re2.score)
		            return -1;
		        return 0;
		    }
		});		
	}

}
