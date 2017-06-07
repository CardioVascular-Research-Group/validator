package edu.jhu.icm.validator.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import edu.jhu.icm.validator.lookup.ChartItemLookup;
import edu.jhu.icm.validator.lookup.InfectionLookup;
import edu.jhu.icm.validator.model.ChartEvent;
import edu.jhu.icm.validator.model.ShockTableLine;

public class AchHeaParser {

	private HashMap<String, ArrayList<ChartEvent>> info;
	
	public AchHeaParser() {

		this("Z:\\STUDY_00109\\509N1396835904-20140406-141326.hea");

	}

	public AchHeaParser(String inputFilePath) {

		try {
			System.out.print(new File(inputFilePath).getName() + ",");
			FileReader reader = new FileReader(inputFilePath);
			BufferedReader br = new BufferedReader(reader); 
			String inputLine = br.readLine(); 
			String[] temp = inputLine.split("\\s+125\\s+");
			System.out.print(temp[1] + ",");
			int increment = new Integer(temp[1]).intValue() * 8;
			SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			temp = inputFilePath.split("-");
			Date startTimePoint = myFormat.parse(temp[1] + temp[2].replaceAll(".hea", "") + ",");
			System.out.print(myFormat.format(startTimePoint) + ",");
			Calendar c = Calendar.getInstance();
			c.setTime(startTimePoint);
			System.out.print(c.getTimeInMillis() + ",");
			c.add(Calendar.MILLISECOND, increment);
			System.out.print(increment + ",");
			System.out.print(c.getTimeInMillis() + ",");
			Date endTimePoint = new Date(c.getTimeInMillis());
			System.out.println(myFormat.format(endTimePoint));
			br.close();
			reader.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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

		File rootDirContents = new File ("z:\\STUDY_02999\\");
		ArrayList<String> dataFiles = new ArrayList<String>();
		getDirectoryContents(rootDirContents, dataFiles);
		for (String filePath : dataFiles) {
			new AchHeaParser(filePath);
		}

//		new AchHeaParser();
	}

	private static ArrayList<String> getDirectoryContents(File dir, ArrayList<String> dataFiles) {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					dataFiles = getDirectoryContents(file, dataFiles);
				} else {
					if((file.getCanonicalPath().endsWith(".hea")))
						dataFiles.add(file.getCanonicalPath());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataFiles;
	}
	
}
