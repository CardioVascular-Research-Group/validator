package edu.jhu.icm.validator.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import edu.jhu.icm.validator.lookup.MedItemLookup;
import edu.jhu.icm.validator.model.MedEvent;

public class MedDataParser {

	private HashMap<String, ArrayList<MedEvent>> info;
	
	public MedDataParser() {

		this("G:\\MIMIC2\\3-io.csv", new HashMap<String,ArrayList<MedEvent>>());

	}

	public MedDataParser(String inputFilePath, HashMap<String,ArrayList<MedEvent>> info) {

		try {
			System.out.println(inputFilePath);
			MedItemLookup medIL = new MedItemLookup(); 
			FileReader reader = new FileReader(inputFilePath);
			BufferedReader br = new BufferedReader(reader); 
			String inputLine; 
			br.readLine();
			br.readLine(); //skip the first two
			String subjectId = "", icuStayId = "", chartTime  = "", label = "", volume = "", dose = "", doseUom = "", solutionId = "", solVolume = "", solUnits = "", route = "";       
			while((inputLine = br.readLine()) != null) { 
				ArrayList<MedEvent> temp = new ArrayList<MedEvent>();
				String[] splitter = inputLine.split("\\|");
				if (splitter.length > 1) {
					subjectId = splitter[0].trim();
					if (info.containsKey(subjectId)) temp = info.get(subjectId);
					icuStayId  = splitter[1].trim();
					label = medIL.getMedItems().get(splitter[2].trim());
					chartTime  = splitter[3].trim();
					volume = splitter[4].trim();      
					dose = splitter[5].trim(); 
					doseUom = splitter[6].trim(); 
					solutionId = splitter[7].trim(); 
					solVolume = splitter[8].trim(); 
					solUnits = splitter[9].trim(); 
					route = splitter[10].trim(); 
					MedEvent medEvent = new MedEvent();
					medEvent.setSubject_id(subjectId);
					medEvent.setIcustay_id(icuStayId);
					medEvent.setCharttime(chartTime);
					medEvent.setLabel(label);
					medEvent.setVolume(volume);
					medEvent.setDose(dose);
					medEvent.setDoseuom(doseUom);;
					medEvent.setSolutionid(solutionId);
					medEvent.setSolvolume(solVolume);
					medEvent.setSolunits(solUnits);
					medEvent.setRoute(route);
					temp.add(medEvent);
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

	public HashMap<String, ArrayList<MedEvent>> getInfo() {
		return info;
	}

	public void setInfo(HashMap<String, ArrayList<MedEvent>> info) {
		this.info = info;
	}

	public static void main(String[] args) throws Exception {

//		File rootDirContents = new File ("G:\\MIMIC2\\suchiSubjects\\");
//		ArrayList<String> dataFiles = new ArrayList<String>();
//		getDirectoryContents(rootDirContents, dataFiles);
//		for (String filePath : dataFiles) {
//			new ChartDataParser(filePath);
//		}

		new MedDataParser();
	}

	private static ArrayList<String> getDirectoryContents(File dir, ArrayList<String> dataFiles) {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					dataFiles = getDirectoryContents(file, dataFiles);
				} else {
					if((file.getCanonicalPath().endsWith("io.csv")))
						dataFiles.add(file.getCanonicalPath());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataFiles;
	}
}
