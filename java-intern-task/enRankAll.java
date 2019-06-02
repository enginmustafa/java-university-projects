import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class enRankAll {

	public enRankAll(ArrayList<reportedEmployee> employees,reportDefinition report) throws IOException {
		
		//percentile
		double rank = (report.topPerformersThreshold / 100.0) * employees.size();
		int castedRank = 0;
		
		//prevent array out of bounds
		if(rank>=1) {castedRank=((int)(rank)-1);}
		
        reportedEmployee barage = employees.get(castedRank); 
        double barageScore = barage.score;
        
        String csvFile = "reportResults.csv";
        FileWriter writer = new FileWriter(csvFile);
        writer.append("NAME  ,SCORE \n");
        
        for(reportedEmployee item : employees) {
        	if(item.score<=barageScore) {
  		  //System.out.println("TO ENLIST:"+item.name+" "+item.score);
  		  generateOutputFile(item.name,item.score,writer);
        	}
  	    } 
        writer.flush(); 
        writer.close();
        System.out.println("CSV File created. ("+csvFile+")");
        
	}

	private void generateOutputFile(String name, double score, FileWriter writer) throws IOException {
		writer.append(name);
        writer.append(",  ");
        writer.append(String.valueOf(score));
        writer.append("\n");		
	}
}
