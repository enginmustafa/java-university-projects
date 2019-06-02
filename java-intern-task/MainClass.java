
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.simple.parser.ParseException;

public class MainClass {
	public static void main(String[] args) throws IOException, ParseException {
      start();
      
	}

	private static void start() throws IOException {
		BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
		
         System.out.println("Enter path to data file: "); 
         String dataPath = reader.readLine();
         
         System.out.println("Enter path to report file: "); 
         String reportPath = reader.readLine();
         
		 init(dataPath,reportPath);
		 
		 /*
		 ---My paths(test)---
		 String dP="C:\\Users\\User\\Desktop\\intern\\data.json";
		 String rP="C:\\Users\\User\\Desktop\\intern\\reportDefinition.json";
		 init(dP,rP);
		 */
	}

	private static void init(String dPath, String rPath) throws IOException {
		
		  //load report file
		  reportDefinition report = new reportDefinition(rPath);
		  
		  //load data file, eliminate by first criteria, sort arrayList
		  getData data = new getData(dPath,report);
		  
		  //eliminate by second criteria, generate csv file with employees left
		  new enRankAll(data.checkedEmployees,report);
	}


	
}
