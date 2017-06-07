package edu.jhu.icm.validator.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import edu.jhu.icm.validator.lookup.ChartItemLookup;
import edu.jhu.icm.validator.lookup.InfectionLookup;
import edu.jhu.icm.validator.model.ChartEvent;
import edu.jhu.icm.validator.model.ShockTableLine;

public class ChartDataParser {

	private HashMap<String, ArrayList<ChartEvent>> info;
	
	public ChartDataParser() {

		this("G:\\MIMIC2\\3-chart.csv", new HashMap<String,ArrayList<ChartEvent>>());

	}

	public ChartDataParser(String inputFilePath, HashMap<String,ArrayList<ChartEvent>> info) {

		try {
			System.out.println(inputFilePath);
			ChartItemLookup cIL = new ChartItemLookup(); 
			FileReader reader = new FileReader(inputFilePath);
			BufferedReader br = new BufferedReader(reader); 
			String inputLine; 
			br.readLine();
			br.readLine(); //skip the first two
			String subjectId = "", icuStayId = "", itemName = "", chartTime = "", value1 = "", value1num = "", value1uom = "", value2 = "", value2num = "", value2uom = "";
			while((inputLine = br.readLine()) != null) { 
				ArrayList<ChartEvent> temp = new ArrayList<ChartEvent>();
				String[] splitter = inputLine.split("\\|");
				if (splitter.length > 1) {
					subjectId = splitter[0].trim();
					if (info.containsKey(subjectId)) temp = info.get(subjectId);
					icuStayId = splitter[1].trim();
					itemName = cIL.getChartItems().get(splitter[2].trim());
					chartTime = splitter[3].trim();
					value1 = splitter[4].trim();
					value1num = splitter[5].trim();
					value1uom = splitter[6].trim();
					value2 = splitter[7].trim();
					value2num = splitter[8].trim();
					value2uom = splitter[9].trim();			
					ChartEvent chartEvent = new ChartEvent();
					chartEvent.setSubject_id(subjectId);
					chartEvent.setIcustay_id(icuStayId);
					chartEvent.setCharttime(chartTime);
					chartEvent.setLabel(itemName);
					chartEvent.setValue1(value1);
					chartEvent.setValue1num(value1num);
					chartEvent.setValue1uom(value1uom);
					chartEvent.setValue2(value2);
					chartEvent.setValue2num(value2num);
					chartEvent.setValue2uom(value2uom);
					temp.add(chartEvent);
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

	public HashMap<String, ArrayList<ChartEvent>> getInfo() {
		return info;
	}

	public void setInfo(HashMap<String, ArrayList<ChartEvent>> info) {
		this.info = info;
	}

	public static void main(String[] args) throws Exception {

//		File rootDirContents = new File ("G:\\MIMIC2\\suchiSubjects\\");
//		ArrayList<String> dataFiles = new ArrayList<String>();
//		getDirectoryContents(rootDirContents, dataFiles);
//		for (String filePath : dataFiles) {
//			new ChartDataParser(filePath);
//		}

		new ChartDataParser();
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
