package edu.jhu.icm.validator.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import edu.jhu.icm.validator.model.Result;

public class ResultsDataParser {

	private HashMap<String, ArrayList<Result>> info;
	
	public ResultsDataParser() {

		this("G:\\MIMIC2\\3-results.csv", new HashMap<String,ArrayList<Result>>());

	}

	public ResultsDataParser(String inputFilePath, HashMap<String,ArrayList<Result>> info) {

		try {
			System.out.println(inputFilePath);
			FileReader reader = new FileReader(inputFilePath);
			BufferedReader br = new BufferedReader(reader); 
			String inputLine; 
			br.readLine(); //skip the first one
			String  subjectId = "", chartTime = "", hr = "", rr = "", paCO2 = "", tValue = "", wbc = "", infection = "", sbp = "", dbp = "", lacticAcid = "", fluidTotal = "", severityLevel = "";
			while((inputLine = br.readLine()) != null) { 
				ArrayList<Result> temp = new ArrayList<Result>();
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
					severityLevel = splitter[16].trim();
					Result result = new Result();
					result.setSubject_id(subjectId);
					result.setChartTime(chartTime);
					result.setHr(hr);
					result.setRr(rr);
					result.setPaCO2(paCO2);
					result.setTemp(tValue);
					result.setWbc(wbc);
					result.setInfection(infection);
					result.setSbp(sbp);
					result.setDbp(dbp);
					result.setLacticAcid(lacticAcid);
					result.setFluidTotal(fluidTotal);
					result.setSeverityLevel(severityLevel);
					temp.add(result);
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

	public HashMap<String, ArrayList<Result>> getInfo() {
		return info;
	}

	public void setInfo(HashMap<String, ArrayList<Result>> info) {
		this.info = info;
	}

	public static void main(String[] args) throws Exception {

//		File rootDirContents = new File ("G:\\MIMIC2\\suchiSubjects\\");
//		ArrayList<String> dataFiles = new ArrayList<String>();
//		getDirectoryContents(rootDirContents, dataFiles);
//		for (String filePath : dataFiles) {
//			new ChartDataParser(filePath);
//		}

		new ResultsDataParser();
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
