package edu.jhu.icm.validator.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import edu.jhu.icm.validator.model.Aggregation;

public class AggregateDataParser {

	private HashMap<String, ArrayList<Aggregation>> info;
	
	public AggregateDataParser() {

		this("G:\\MIMIC2\\3-aggregation.csv", new HashMap<String,ArrayList<Aggregation>>());

	}

	public AggregateDataParser(String inputFilePath, HashMap<String,ArrayList<Aggregation>> info) {

		try {
			System.out.println(inputFilePath);
			FileReader reader = new FileReader(inputFilePath);
			BufferedReader br = new BufferedReader(reader); 
			String inputLine; 
			br.readLine(); //skip the first one
			String  subjectId = "", chartTime = "", hr = "", rr = "", paCO2 = "", tValue = "", wbc = "", infection = "", sbp = "", dbp = "", lacticAcid = "", fluidTotal = "";
			while((inputLine = br.readLine()) != null) { 
				ArrayList<Aggregation> temp = new ArrayList<Aggregation>();
				String[] splitter = inputLine.split(",");
				if (splitter.length > 1) {
					subjectId = splitter[0].trim();
					if (info.containsKey(subjectId)) temp = info.get(subjectId);
					chartTime = splitter[1].trim();
					hr = splitter[2].trim();
					rr = splitter[3].trim();
					paCO2 = splitter[4].trim();
					tValue = splitter[5].trim();
					wbc = splitter[6].trim();
					infection = splitter[8].trim();
					sbp = splitter[10].trim();
					dbp = splitter[11].trim();
					lacticAcid = splitter[12].trim();
					fluidTotal = splitter[14].trim();
					Aggregation aggregation = new Aggregation();
					aggregation.setSubject_id(subjectId);
					aggregation.setChartTime(chartTime);
					aggregation.setHr(hr);
					aggregation.setRr(rr);
					aggregation.setPaCO2(paCO2);
					aggregation.setTemp(tValue);
					aggregation.setWbc(wbc);
					aggregation.setInfection(infection);
					aggregation.setSbp(sbp);
					aggregation.setDbp(dbp);
					aggregation.setLacticAcid(lacticAcid);
					aggregation.setFluidTotal(fluidTotal);
					temp.add(aggregation);
					info.put(subjectId, temp);
				}
			} 
			
			setInfo(info);				
			br.close();
			reader.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public HashMap<String, ArrayList<Aggregation>> getInfo() {
		return info;
	}

	public void setInfo(HashMap<String, ArrayList<Aggregation>> info) {
		this.info = info;
	}

	public static void main(String[] args) throws Exception {

//		File rootDirContents = new File ("G:\\MIMIC2\\suchiSubjects\\");
//		ArrayList<String> dataFiles = new ArrayList<String>();
//		getDirectoryContents(rootDirContents, dataFiles);
//		for (String filePath : dataFiles) {
//			new ChartDataParser(filePath);
//		}

		new AggregateDataParser();
	}

	private static ArrayList<String> getDirectoryContents(File dir, ArrayList<String> dataFiles) {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					dataFiles = getDirectoryContents(file, dataFiles);
				} else {
					if((file.getCanonicalPath().endsWith("chart.csv")))
						dataFiles.add(file.getCanonicalPath());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataFiles;
	}
	
}
