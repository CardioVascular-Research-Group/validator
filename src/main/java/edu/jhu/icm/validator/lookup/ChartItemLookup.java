package edu.jhu.icm.validator.lookup;

import java.util.HashMap;

public class ChartItemLookup {

	private HashMap<String,String> chartItems = new HashMap<String,String>();
	
	public ChartItemLookup() {
		
		HashMap<String,String> temp = new HashMap<String,String>();
		temp.put("51","Arterial BP");
		temp.put("52","Arterial BP Mean");
		temp.put("455","NBP");
		temp.put("113","CVP");
		temp.put("190","FiO2 Set");
		temp.put("198","GCS Total");
		temp.put("211","Heart Rate");
		temp.put("455","NBP");
		temp.put("490","PAO2");
		temp.put("618","Respiratory Rate");
		temp.put("676","Temperature C");
		temp.put("677","Temperature C (calc)");
		temp.put("678","Temperature F");
		temp.put("679","Temperature F (calc)");
		temp.put("778","Arterial PaCO2");
		temp.put("779","Arterial PaO2");
		temp.put("781","BUN (6-20)");
		temp.put("791","Creatinine (0-1.3)");
		temp.put("813","Hematocrit");
		temp.put("814","Hemoglobin");
		temp.put("818","Lactic Acid(0.5-2.0)");
		temp.put("828","Platelets");
		temp.put("829","Potassium (3.5-5.3)");
		temp.put("3420","FIO2");
		temp.put("861","WBC (4-11,000)");
		temp.put("1126","Art.pH");
		temp.put("1127","WBC   (4-11,000)");
		temp.put("1162","BUN");
		temp.put("4753","pH (Art)");
		temp.put("4948","Bilirubin");
		temp.put("5876","bun");
		temp.put("4200","WBC 4.0-11.0");
		temp.put("3605","Respiratory Support");
		temp.put("3737","BUN    (6-20)");
		temp.put("3750","Creatinine   (0-0.7)");
		temp.put("3761","Hematocrit (35-51)");
		temp.put("1525","Creatinine");
		temp.put("1531","Lactic Acid");
		temp.put("1542","WBC");
		temp.put("6701","Arterial BP #2");
		temp.put("6702","Arterial BP Mean #2");
		temp.put("6926","Arterial BP #3");
		temp.put("6927","Arterial Mean #3");
		setChartItems(temp);
	}

	public HashMap<String, String> getChartItems() {
		return chartItems;
	}

	public void setChartItems(HashMap<String, String> chartItems) {
		this.chartItems = chartItems;
	}
	
}
