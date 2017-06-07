package edu.jhu.icm.validator.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import edu.jhu.icm.validator.lookup.IOItemLookup;
import edu.jhu.icm.validator.model.IoEvent;

public class IoDataParser {

	private HashMap<String, ArrayList<IoEvent>> info;
	
	public IoDataParser() {

		this("G:\\MIMIC2\\3-io.csv", new HashMap<String,ArrayList<IoEvent>>());

	}

	public IoDataParser(String inputFilePath, HashMap<String,ArrayList<IoEvent>> info) {

		try {
			System.out.println(inputFilePath);
			IOItemLookup ioIL = new IOItemLookup(); 
			FileReader reader = new FileReader(inputFilePath);
			BufferedReader br = new BufferedReader(reader); 
			String inputLine; 
			br.readLine();
			br.readLine(); //skip the first two
			String subjectId = "", icuStayId = "", chartTime  = "", elemId = "", altId = "", realTime = "", cgId = "", cuId = "", volume = "", volumeUom = "", unitsHung = "", unitsHungUom = "", newBottle = "", stopped = "", estimate = "", label = "";       
			while((inputLine = br.readLine()) != null) { 
				ArrayList<IoEvent> temp = new ArrayList<IoEvent>();
				String[] splitter = inputLine.split("\\|");
				if (splitter.length > 1) {
					label = ioIL.getIoItems().get(splitter[0].trim());
					subjectId = splitter[1].trim();
					if (info.containsKey(subjectId)) temp = info.get(subjectId);
					icuStayId  = splitter[2].trim();
					chartTime  = splitter[3].trim();
					elemId = splitter[4].trim(); 
					altId = splitter[5].trim(); 
					realTime = splitter[6].trim();       
					cgId = splitter[7].trim(); 
					cuId = splitter[8].trim(); 
					volume = splitter[9].trim();      
					volumeUom = splitter[10].trim(); 
					unitsHung = splitter[11].trim(); 
					unitsHungUom = splitter[12].trim(); 
					newBottle = splitter[13].trim(); 
					stopped = splitter[14].trim(); 
					estimate = splitter[15].trim();		
					IoEvent ioEvent = new IoEvent();
					ioEvent.setSubject_id(subjectId);
					ioEvent.setIcustay_id(icuStayId);
					ioEvent.setCharttime(chartTime);
					ioEvent.setLabel(label);
					ioEvent.setElemid(elemId);
					ioEvent.setAltid(altId);
					ioEvent.setRealtime(realTime);
					ioEvent.setCgid(cgId);
					ioEvent.setCuid(cuId);
					ioEvent.setVolume(volume);      
					ioEvent.setVolumeuom(volumeUom); 
					ioEvent.setUnitshung(unitsHung); 
					ioEvent.setUnitshunguom(unitsHungUom); 
					ioEvent.setNewbottle(newBottle); 
					ioEvent.setStopped(stopped); 
					ioEvent.setEstimate(estimate);
					temp.add(ioEvent);
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

	public HashMap<String, ArrayList<IoEvent>> getInfo() {
		return info;
	}

	public void setInfo(HashMap<String, ArrayList<IoEvent>> info) {
		this.info = info;
	}

	public static void main(String[] args) throws Exception {

//		File rootDirContents = new File ("G:\\MIMIC2\\suchiSubjects\\");
//		ArrayList<String> dataFiles = new ArrayList<String>();
//		getDirectoryContents(rootDirContents, dataFiles);
//		for (String filePath : dataFiles) {
//			new ChartDataParser(filePath);
//		}

		new IoDataParser();
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
