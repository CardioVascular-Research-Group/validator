package edu.jhu.icm.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.jhu.icm.validator.io.AggregateDataParser;
import edu.jhu.icm.validator.io.ChartDataParser;
import edu.jhu.icm.validator.io.IoDataParser;
import edu.jhu.icm.validator.io.MedDataParser;
import edu.jhu.icm.validator.io.ResultsDataParser;
import edu.jhu.icm.validator.lookup.InfectionLookup;
import edu.jhu.icm.validator.model.Aggregation;
import edu.jhu.icm.validator.model.ChartEvent;
import edu.jhu.icm.validator.model.IoEvent;
import edu.jhu.icm.validator.model.MedEvent;
import edu.jhu.icm.validator.model.Result;
import edu.jhu.icm.validator.model.ShockTableLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Facade for Mimic2SepsisFinder.
 * Created by sgranite on 5/19/17.
 */
@Component
public class validatorFacade {

	@Autowired
	private String fileName;

	@Autowired
	private String severeFileName;

	@Autowired
	private String shockFileName;

	@Autowired
	private String tableFile;

	@Autowired
	private String subjectsWithSepsisFile;

	@Autowired
	private String subjectsWithSevereFile;

	@Autowired
	private String subjectsWithShockFile;

	@Autowired
	private String physionetMatchedDir;

	public void validateTrew(String filename, boolean directory, boolean resultsFlag) throws IOException, ParseException {

		ArrayList<String> resultsToProcess = new ArrayList<String>();
		if (filename.isEmpty()) filename = fileName;
		String tableFile = "";
		if (directory) {
			resultsToProcess = getDirectoryContents(new File(filename), resultsToProcess, "results.csv");
			tableFile = filename + "\\cumulativeResults.txt";
		} else {
			resultsToProcess.add(filename);
			File temp = new File(filename);
			tableFile = temp.getParent() + "\\cumulativeResults.txt";
		}

		int count = 1, nothingCount = 0, sirsCount = 0, sepsisCount = 0, severeCount = 0, shockCount = 0;
		for (String resultToProcess : resultsToProcess) {

			HashMap<String,ArrayList<Result>> info = new HashMap<String,ArrayList<Result>>();
			ResultsDataParser rdp = new ResultsDataParser(resultToProcess, info);
			info = rdp.getInfo();
			Set<String> keys = info.keySet();
			TreeSet<String> sortedKeys = new TreeSet<String>(keys);
			int severity = 0;
			String subject = "", chartTime = "";
			FileWriter writer;
			if (count < 2) {
				writer = new FileWriter(tableFile);
				writer.write("Subject,MaxSeverityLevel,Sirs/Sepsis/Severe/Shock\r\n");
			} else {
				writer = new FileWriter(tableFile, true);				
			}
			
			for (String key : sortedKeys) {

				ArrayList<Result> results = info.get(key);
				for (Result result : results) {

					subject = result.getSubject_id();
					if (severity < new Integer(result.getSeverityLevel()).intValue()) {
						severity = new Integer(result.getSeverityLevel()).intValue();
						chartTime = result.getChartTime();
					}
					
				}
			}
			writer.write(subject + "," + severity + "," + chartTime + ",");
			if (severity == 0) {
				writer.write("nothing\r\n");
				nothingCount++;
			}	else if (severity == 1) {
				writer.write("sirs\r\n");
				sirsCount++;
			} else if (severity == 2) {
				writer.write("sepsis\r\n");
				sepsisCount++;
			} else if (severity == 3) {
				writer.write("severe\r\n");
				severeCount++;
			} else {
				writer.write("shock\r\n");				
				shockCount++;
			}

			writer.close();
			count++;
		}

		System.out.println("Nothing Count: " + nothingCount);
		System.out.println("Sirs Count: " + sirsCount);
		System.out.println("Sepsis Count: " + sepsisCount);
		System.out.println("Severe Count: " + severeCount);
		System.out.println("Shock Count: " + shockCount);

	}
	
	public void validateTrew(String filename, boolean directory) throws IOException, ParseException {

		ArrayList<String> chartsToProcess = new ArrayList<String>();
		ArrayList<String> iosToProcess = new ArrayList<String>();
		ArrayList<String> medsToProcess = new ArrayList<String>();
		if (filename.isEmpty()) filename = fileName;
		if (directory) {
			chartsToProcess = getDirectoryContents(new File(filename), chartsToProcess, "chart.csv");
			iosToProcess = getDirectoryContents(new File(filename), iosToProcess, "io.csv");
			medsToProcess = getDirectoryContents(new File(filename), medsToProcess, "med.csv");
		} else {
			chartsToProcess.add(filename);
			iosToProcess.add(filename.replaceAll("chart", "io"));
			medsToProcess.add(filename.replaceAll("chart", "med"));
		}

		InfectionLookup iL = new InfectionLookup();
		HashMap<String,HashMap<String,ShockTableLine>> organizedInfo = new HashMap<String,HashMap<String,ShockTableLine>>();

		for (String chartToProcess : chartsToProcess) {

			HashMap<String,ArrayList<ChartEvent>> info = new HashMap<String,ArrayList<ChartEvent>>();
			ChartDataParser cdp = new ChartDataParser(chartToProcess, info);
			info = cdp.getInfo();
			Set<String> keys = info.keySet();
			TreeSet<String> sortedKeys = new TreeSet<String>(keys);
			for (String key : sortedKeys) {

				ArrayList<ChartEvent> temp = info.get(key);
				HashMap<String,ShockTableLine> line = new HashMap<String,ShockTableLine>();
				for (ChartEvent entry : temp)	{
					if (organizedInfo.containsKey(key)) line = organizedInfo.get(key);
					ShockTableLine tempLine = new ShockTableLine();
					if (line.containsKey((entry.getCharttime()))) tempLine = line.get(entry.getCharttime());
					if (entry.getLabel().equalsIgnoreCase("Heart Rate")) {
						tempLine.setHR(entry.isSirsFlag());
						tempLine.setHRNull(entry.isSirsFlagNull());
						if(!(tempLine.isHRNull())) tempLine.setHrValue(new Double(entry.getValue1()));
					} else if (entry.getLabel().equalsIgnoreCase("Respiratory Rate")) {
						tempLine.setRR(entry.isSirsFlag());
						tempLine.setRRNull(entry.isSirsFlagNull());
						if(!(tempLine.isRRNull())) tempLine.setRrValue(new Double(entry.getValue1()));
					} else if (entry.getLabel().startsWith("Temperature")) {
						tempLine.setTemp(entry.isSirsFlag());
						tempLine.setTempNull(entry.isSirsFlagNull());
						if(!(tempLine.isTempNull())) tempLine.setTValue(new Double(entry.getValue1()));
					} else if (entry.getLabel().startsWith("WBC")) {
						tempLine.setWBC(entry.isSirsFlag());
						tempLine.setWBCNull(entry.isSirsFlagNull());
						if(!(tempLine.isWBCNull())) tempLine.setWbcValue(new Double(entry.getValue1()));
					} else if (entry.getLabel().endsWith("PaCO2")) {
						tempLine.setPaco2(entry.isSirsFlag());
						tempLine.setPaco2Null(entry.isSirsFlagNull());
						if(!(tempLine.isPaco2Null())) tempLine.setPaco2Value(new Double(entry.getValue1()));
					}

					if (iL.getInfections().contains(key)) {
						tempLine.setInfection(true);
					}

					if ((entry.getLabel().equalsIgnoreCase("Arterial BP")) || (entry.getLabel().equalsIgnoreCase("Arterial BP #2")) 
							|| (entry.getLabel().equalsIgnoreCase("Arterial BP #3"))) { // || (entry.getLabel().equalsIgnoreCase("NBP"))) {
						tempLine.setBP(entry.isSevereFlag());
						tempLine.setBPNull(entry.isSevereFlagNull());
						if(!(tempLine.isBPNull())) {
							if (!entry.getValue1().equalsIgnoreCase("")) {
								tempLine.setSbpValue(new Double(entry.getValue1()));
							} else {
								tempLine.setSbpValue(new Double("0"));								
							}
							if (!entry.getValue2().equalsIgnoreCase("")) {
								tempLine.setDbpValue(new Double(entry.getValue2()));
							} else {
								tempLine.setDbpValue(new Double("0"));								
							}
						}
					} else if (entry.getLabel().startsWith("Lactic Acid")) {
						tempLine.setLacticAcid(entry.isSevereFlag());
						tempLine.setLacticAcidNull(entry.isSevereFlagNull());
						if(!(tempLine.isLacticAcidNull())) tempLine.setLacticAcidValue(new Double(entry.getValue1()));
					} else if (entry.getLabel().startsWith("pH") || entry.getLabel().endsWith("pH")) {
						tempLine.setpH(entry.isSevereFlag());
						tempLine.setpHNull(entry.isSevereFlagNull());
					} 

					line.put(entry.getCharttime(), tempLine);
				}

				organizedInfo.put(key, line);

			}

		}

		SimpleDateFormat mimic2Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		for (String ioToProcess : iosToProcess) {

			HashMap<String,ArrayList<IoEvent>> info = new HashMap<String,ArrayList<IoEvent>>();
			IoDataParser idp = new IoDataParser(ioToProcess, info);
			info = idp.getInfo();
			Set<String> keys = info.keySet();
			TreeSet<String> sortedKeys = new TreeSet<String>(keys);
			double localFluidTotal = 0;

			for (String key : sortedKeys) {

				ArrayList<IoEvent> temp = info.get(key);
				String lastTime = null;
				HashMap<String,ShockTableLine> line = new HashMap<String,ShockTableLine>();
				for (IoEvent entry : temp)	{

					if (organizedInfo.containsKey(key)) line = organizedInfo.get(key);
					ShockTableLine tempLine = new ShockTableLine();
					c.setTime(mimic2Format.parse(entry.getCharttime()));
					c.add(Calendar.HOUR, -24);
					if (line.containsKey((entry.getCharttime()))) tempLine = line.get(entry.getCharttime());
					if (iL.getInfections().contains(key)) {
						tempLine.setInfection(true);
					}
					if (!(entry.getVolume().isEmpty())) {
						if (entry.getLabel().toLowerCase().contains("out")) {
							tempLine.setLineFluid(-new Double(entry.getVolume()).doubleValue());
						} else {
							tempLine.setLineFluid(new Double(entry.getVolume()).doubleValue());
						}
						localFluidTotal += tempLine.getLineFluid();
					}
					if (line.containsKey(mimic2Format.format(c.getTime())) && (!(entry.getCharttime().equalsIgnoreCase(lastTime)))) 
						localFluidTotal -= line.get(mimic2Format.format(c.getTime())).getLineFluid();
					tempLine.setFluidTotal(localFluidTotal);					
					line.put(entry.getCharttime(), tempLine);
					lastTime = entry.getCharttime();
				}
				organizedInfo.put(key, line);
			}
		}

//		for (String medToProcess : medsToProcess) {
//
//			HashMap<String,ArrayList<MedEvent>> info = new HashMap<String,ArrayList<MedEvent>>();
//			MedDataParser mdp = new MedDataParser(medToProcess, info);
//			info = mdp.getInfo();
//			Set<String> keys = info.keySet();
//			TreeSet<String> sortedKeys = new TreeSet<String>(keys);
//			double localFluidTotal = 0;
//
//			for (String key : sortedKeys) {
//
//				ArrayList<MedEvent> temp = info.get(key);
//				String lastTime = null;
//				HashMap<String,ShockTableLine> line = new HashMap<String,ShockTableLine>();
//				for (MedEvent entry : temp)	{
//
//					if (organizedInfo.containsKey(key)) line = organizedInfo.get(key);
//					ShockTableLine tempLine = new ShockTableLine();
//					c.setTime(mimic2Format.parse(entry.getCharttime()));
//					c.add(Calendar.HOUR, -24);
//					if (line.containsKey((entry.getCharttime()))) tempLine = line.get(entry.getCharttime());
//					if (iL.getInfections().contains(key)) {
//						tempLine.setInfection(true);
//					}
//					if (!(entry.getVolume().isEmpty())) {
//						if (entry.getLabel().toLowerCase().contains("out")) {
//							tempLine.setLineFluid(-new Double(entry.getVolume()).doubleValue());
//						} else {
//							tempLine.setLineFluid(new Double(entry.getVolume()).doubleValue());
//						}
//						localFluidTotal += tempLine.getLineFluid();
//					}
//					if (line.containsKey(mimic2Format.format(c.getTime())) && (!(entry.getCharttime().equalsIgnoreCase(lastTime)))) {
//						localFluidTotal -= line.get(mimic2Format.format(c.getTime())).getLineFluid();
//					} else {
//						//						System.out.println(0);						
//					}
//					tempLine.setFluidTotal(localFluidTotal);					
//					line.put(entry.getCharttime(), tempLine);
//					lastTime = entry.getCharttime();
//				}
//				organizedInfo.put(key, line);
//			}
//		}

		Set<String> keys = organizedInfo.keySet();
		TreeSet<String> sortedKeys = new TreeSet<String>(keys);

		for (String key : sortedKeys) {
			HashMap<String,ShockTableLine> line = organizedInfo.get(key);
			String tableFile = physionetMatchedDir + key + "-aggregation.csv";
			FileWriter writer = new FileWriter(tableFile);
			writer.write("Subject,ChartTime,HRValue,RRValue,PaCO2Value,TValue,WBCValue,HasSirs,HasInfection,HasSepsis,SBPValue,DBPValue,LAValue,HasSevere,FluidTotal,HasShock,SeverityLevel\r\n");

			Set<String> keys2 = line.keySet();
			TreeSet<String> sortedKeys2 = new TreeSet<String>(keys2);
			for (String key2 : sortedKeys2) {

				ShockTableLine tempLine = line.get(key2);

				//					if (tempLine.isSimultaneous()) {
				writer.write(key + "," + key2 + ",");
				if (tempLine.isHRNull()) {
					writer.write(",");
				} else {
					writer.write(tempLine.getHrValue() + ",");
				}
				if (tempLine.isRRNull()) {
					writer.write(" , ");
				} else {
					writer.write(tempLine.getRrValue() + ",");
				}
				if (tempLine.isPaco2Null()) {
					writer.write(",");
				} else {
					writer.write(tempLine.getPaco2Value() + ",");
				}
				if (tempLine.isTempNull()) {
					writer.write(",");
				} else {
					writer.write(tempLine.getTValue() + ",");
				}
				if (tempLine.isWBCNull()) {
					writer.write(",");
				} else {
					writer.write(tempLine.getWbcValue() + ",");
				}
				writer.write(tempLine.isSimultaneous() + ",");
				if (tempLine.isSepsis()) {
					writer.write("true,true,");
				} else {
					if (tempLine.isInfection()) {
						writer.write("true,false,");
					} else {
						writer.write("false,false,");
					}
				}
				if (tempLine.isBPNull()) {
					writer.write(",,");
				} else {
					writer.write(tempLine.getSbpValue() + "," + tempLine.getDbpValue() + ",");
				}
				if (tempLine.isLacticAcidNull()) {
					writer.write(",");
				} else {
					writer.write(tempLine.getLacticAcidValue() + ",");
				}
				writer.write(tempLine.isSevere() + ",");
				if (tempLine.isFluidNull()) {
					writer.write(",");
				} else {
					writer.write(tempLine.getFluidTotal() + ",");
				}
				writer.write(tempLine.isShock() + "," + tempLine.getSeverityLevel() + "\r\n");

			}
			writer.close();
		}

		ArrayList<String> aggsToProcess = new ArrayList<String>();
		if (directory) {
			aggsToProcess = getDirectoryContents(new File(filename), aggsToProcess, "aggregation.csv");
		} else {
			aggsToProcess.add(filename.replaceAll("chart", "aggregation"));
		}

		for (String aggToProcess : aggsToProcess) {

			HashMap<String,ArrayList<Aggregation>> info = new HashMap<String,ArrayList<Aggregation>>();
			AggregateDataParser adp = new AggregateDataParser(aggToProcess, info);
			info = adp.getInfo();
			keys = info.keySet();
			sortedKeys = null;
			sortedKeys = new TreeSet<String>(keys);
			int count = 1;
			String lastHr = "", lastRr = "", lastPaCO2 = "", lastTemp = "", lastWbc = "", lastSbp = "", lastDbp = "", lastLacticAcid = "", lastFluidTotal = "";
			String tableFile = aggToProcess.replaceAll("aggregation", "results");
			System.out.println(tableFile);
			FileWriter writer = new FileWriter(tableFile);
			writer.write("Subject,ChartTime,HRValue,RRValue,PaCO2Value,TValue,WBCValue,HasSirs,HasInfection,HasSepsis,SBPValue,DBPValue,LAValue,HasSevere,FluidTotal,HasShock,SeverityLevel\r\n");
			for (String key : sortedKeys) {

				ArrayList<Aggregation> aggregates = info.get(key);
				for (Aggregation aggregate : aggregates) {
					if (count > 1) {
						if (((aggregate.getHr().equalsIgnoreCase("") || new Double(aggregate.getHr()).doubleValue() <= 0.0) && new Double(lastHr).doubleValue() >= 0)) aggregate.setHr(lastHr); 
						if (((aggregate.getRr().equalsIgnoreCase("") || new Double(aggregate.getRr()).doubleValue() <= 0.0) && new Double(lastRr).doubleValue() >= 0))  aggregate.setRr(lastRr); 
						if (((aggregate.getPaCO2().equalsIgnoreCase("") || new Double(aggregate.getPaCO2()).doubleValue() <= 0.0) && new Double(lastPaCO2).doubleValue() >= 0)) aggregate.setPaCO2(lastPaCO2); 
						if (((aggregate.getTemp().equalsIgnoreCase("") || new Double(aggregate.getTemp()).doubleValue() <= 0.0) && new Double(lastTemp).doubleValue() >= 0)) aggregate.setTemp(lastTemp); 
						if (((aggregate.getWbc().equalsIgnoreCase("") || new Double(aggregate.getWbc()).doubleValue() <= 0.0) && new Double(lastWbc).doubleValue() >= 0)) aggregate.setWbc(lastWbc); 
						if (((aggregate.getSbp().equalsIgnoreCase("") || new Double(aggregate.getSbp()).doubleValue() <= 0.0) && new Double(lastSbp).doubleValue() >= 0)) aggregate.setSbp(lastSbp); 
						if (((aggregate.getDbp().equalsIgnoreCase("") || new Double(aggregate.getDbp()).doubleValue() <= 0.0) && new Double(lastDbp).doubleValue() >= 0)) aggregate.setDbp(lastDbp); 
						if (((aggregate.getLacticAcid().equalsIgnoreCase("") || new Double(aggregate.getLacticAcid()).doubleValue() <= 0.0) && new Double(lastLacticAcid).doubleValue() >= 0)) aggregate.setLacticAcid(lastLacticAcid); 
						if (((aggregate.getFluidTotal().equalsIgnoreCase("") || new Double(aggregate.getFluidTotal()).doubleValue() <= 0.0))) aggregate.setFluidTotal(lastFluidTotal); 
					} else {
						if (aggregate.getHr().equalsIgnoreCase("")) aggregate.setHr("0.0"); 
						if (aggregate.getRr().equalsIgnoreCase(""))  aggregate.setRr("0.0"); 
						if (aggregate.getPaCO2().equalsIgnoreCase("")) aggregate.setPaCO2("0.0"); 
						if (aggregate.getTemp().equalsIgnoreCase("")) aggregate.setTemp("0.0"); 
						if (aggregate.getWbc().equalsIgnoreCase("")) aggregate.setWbc("0.0"); 
						if (aggregate.getSbp().equalsIgnoreCase("")) aggregate.setSbp("0.0"); 
						if (aggregate.getDbp().equalsIgnoreCase("")) aggregate.setDbp("0.0"); 
						if (aggregate.getLacticAcid().equalsIgnoreCase("")) aggregate.setLacticAcid("0.0"); 
						if (aggregate.getFluidTotal().equalsIgnoreCase("")) aggregate.setFluidTotal("0.0"); 
					}
					lastHr = aggregate.getHr();
					lastRr = aggregate.getRr();
					lastPaCO2 = aggregate.getPaCO2(); 
					lastTemp = aggregate.getTemp();
					lastWbc = aggregate.getWbc(); 
					lastSbp = aggregate.getSbp();
					lastDbp = aggregate.getDbp();
					lastLacticAcid = aggregate.getLacticAcid();
					lastFluidTotal = aggregate.getFluidTotal();	
					writer.write(key + "," + aggregate.getChartTime() + "," + lastHr + "," + lastRr + "," + lastPaCO2 + "," + lastTemp + "," + lastWbc + ",");
					int hasSirs = 0, severity = 0;
					if (new Double(lastHr).doubleValue() > 90) hasSirs++;
					if (new Double(lastRr).doubleValue() > 20) hasSirs++;
					if (new Double(lastPaCO2).doubleValue() < 32 && new Double(lastPaCO2).doubleValue() > 0) hasSirs++;
					if ((new Double(lastTemp).doubleValue() > 100.4) || ((new Double(lastTemp).doubleValue() > 38) && (new Double(lastTemp).doubleValue() < 96.8)) || ((new Double(lastTemp).doubleValue() < 36.0) && (new Double(lastTemp).doubleValue() > 0.0))) hasSirs++;
					if ((new Double(lastWbc).doubleValue() > 12.0) || ((new Double(lastWbc).doubleValue() < 4.0) && (new Double(lastWbc).doubleValue() > 0.0))) hasSirs++;
					if (hasSirs > 1) {
						writer.write("true,");
						severity = 1;
						if (aggregate.getInfection().equalsIgnoreCase("true")) {
							writer.write("true, true, ");
							severity++;
						} else {
							writer.write("false, false, ");							
						}
					} else {
						writer.write("false,");
						if (aggregate.getInfection().equalsIgnoreCase("true")) {
							writer.write("true, false, ");
						} else {
							writer.write("false, false, ");							
						}
					}
					int hasSevere = 0;
					writer.write(lastSbp + "," + lastDbp + "," + lastLacticAcid + ",");
					if (new Double(lastSbp).doubleValue() < 90 && new Double(lastSbp).doubleValue() > 0) hasSevere++;
					if (new Double(lastLacticAcid).doubleValue() > 2) hasSevere++;
					if (hasSevere > 0) {
						if (hasSirs > 1 && aggregate.getInfection().equalsIgnoreCase("true")) {
							writer.write("true,");
							if (severity == 2) severity++;
						} else {
							writer.write("false,");
						}
					} else {
						writer.write("false,");
					}
					writer.write(lastFluidTotal + "," );
					if (new Double(lastFluidTotal).doubleValue() >= 1200) {
						if ((new Double(lastSbp).doubleValue() < 90 && new Double(lastSbp).doubleValue() > 0) || (new Double(lastDbp).doubleValue() < 60 && new Double(lastDbp).doubleValue() > 0)) {
							writer.write("true,");
							if (severity == 3) severity++;
						} else {
							writer.write("false,");
						}
					} else {
						writer.write("false,");
					}
					writer.write(severity + "\r\n" );
					count++;
				}

			}
			writer.close();
		}



	}

	private static ArrayList<String> getDirectoryContents(File dir, ArrayList<String> dataFiles, String fileEnd) {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					dataFiles = getDirectoryContents(file, dataFiles, fileEnd);
				} else {
					if((file.getCanonicalPath().endsWith(fileEnd)))
						dataFiles.add(file.getCanonicalPath());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataFiles;
	}

}
