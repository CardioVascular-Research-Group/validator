package edu.jhu.icm.validator.model;

import edu.jhu.icm.validator.lookup.InfectionLookup;

public class ChartEvent {

	private String subject_id;
	private String icustay_id;
	private String charttime;
	private String label;
	private String value1;
	private String value1num;
	private String value1uom;
	private String value2;
	private String value2num;
	private String value2uom;
	private boolean sirsFlag, sirsFlagNull;
	private boolean sepsisFlag, sepsisFlagNull;
	private boolean severeFlag, severeFlagNull;

	public ChartEvent() {
		setSubject_id("");
		setIcustay_id("");
		setCharttime("");
		setLabel("");
		setValue1("");
		setValue1num("");
		setValue1uom("");
		setValue2("");
		setValue2num("");
		setValue2uom("");
		setSirsFlag(false);
		setSepsisFlag(false);
		setSevereFlag(false);
		setSirsFlagNull(false);
		setSepsisFlagNull(false);
		setSevereFlagNull(false);
	}

	public String getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}

	public String getIcustay_id() {
		return icustay_id;
	}

	public void setIcustay_id(String icustay_id) {
		this.icustay_id = icustay_id;
	}

	public String getCharttime() {
		return charttime;
	}

	public void setCharttime(String charttime) {
		this.charttime = charttime;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue1num() {
		return value1num;
	}

	public void setValue1num(String value1num) {
		this.value1num = value1num;
	}

	public String getValue1uom() {
		return value1uom;
	}

	public void setValue1uom(String value1uom) {
		this.value1uom = value1uom;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue2num() {
		return value2num;
	}

	public void setValue2num(String value2num) {
		this.value2num = value2num;
	}

	public String getValue2uom() {
		return value2uom;
	}

	public void setValue2uom(String value2uom) {
		this.value2uom = value2uom;
	}

	public boolean isSirsFlag() {
		sirsCheck();
		return sirsFlag;
	}

	public void setSirsFlag(boolean sirsFlag) {
		this.sirsFlag = sirsFlag;
	}

	public boolean isSepsisFlag() {
		sepsisCheck();
		return sepsisFlag;
	}

	public void setSepsisFlag(boolean sepsisFlag) {
		this.sepsisFlag = sepsisFlag;
	}

	public boolean isSevereFlag() {
		severeCheck();
		return severeFlag;
	}

	public void setSevereFlag(boolean severeFlag) {
		this.severeFlag = severeFlag;
	}

	public boolean isSirsFlagNull() {
		return sirsFlagNull;
	}

	public void setSirsFlagNull(boolean sirsFlagNull) {
		this.sirsFlagNull = sirsFlagNull;
	}	

	public boolean isSepsisFlagNull() {
		return sepsisFlagNull;
	}

	public void setSepsisFlagNull(boolean sepsisFlagNull) {
		this.sepsisFlagNull = sepsisFlagNull;
	}

	public boolean isSevereFlagNull() {
		return severeFlagNull;
	}

	public void setSevereFlagNull(boolean severeFlagNull) {
		this.severeFlagNull = severeFlagNull;
	}


	public String toString() {
		return getSubject_id() + "," + getIcustay_id() + "," + getCharttime() + "," + getLabel() + "," + getValue1() + "," + getValue1num() + "," + getValue1uom() + "," + getValue2() + "," + getValue2num() + "," + getValue2uom();	
	}

	private void sirsCheck() {
		if (!(value1.isEmpty() && value1num.isEmpty())) {
			if (!(value1.equalsIgnoreCase("no data") || value1num.equalsIgnoreCase("no data") || 
					value1.equalsIgnoreCase("error") || value1num.equalsIgnoreCase("error") || 
					value1.equalsIgnoreCase("") || value1num.equalsIgnoreCase(""))) {
				if (label.equalsIgnoreCase("Heart Rate")) {
					if (new Double(value1).doubleValue() > 90 || new Double(value1num).doubleValue() > 90) {
						setSirsFlag(true);
					}
				} else if (label.equalsIgnoreCase("Respiratory Rate")) {
					if (new Double(value1).doubleValue() > 20 || new Double(value1num).doubleValue() > 20) {
						setSirsFlag(true);
					}			
				} else if (label.startsWith("Temperature")) {
					if ((label.endsWith("F") || label.endsWith("F (calc)"))) {
						if (((new Double(value1).doubleValue() > 100.4) || (new Double(value1).doubleValue() < 96.8)) ||
								((new Double(value1num).doubleValue() > 100.4) || (new Double(value1num).doubleValue() < 96.8)))	{
							setSirsFlag(true);
						}
					} else {
						if (((new Double(value1).doubleValue() > 38.0) || (new Double(value1).doubleValue() < 36.0)) ||
								((new Double(value1num).doubleValue() > 38.0) || (new Double(value1num).doubleValue() < 36.0)))	{
							setSirsFlag(true);
						}				
					}
				} else if (label.startsWith("WBC")) {
					if (((new Double(value1).doubleValue() > 12000.0) || (new Double(value1).doubleValue() < 4000.0)) ||
							((new Double(value1num).doubleValue() > 12000.0) || (new Double(value1num).doubleValue() < 4000.0))) {
						setSirsFlag(true);
					}
				} else if (label.endsWith("PaCO2")) {
					if (new Double(value1).doubleValue() < 32 || new Double(value1num).doubleValue() < 32) {
						setSirsFlag(true);
					}
				}
			} else {
				setSirsFlagNull(true);
			}
		} else {
			setSirsFlagNull(true);
		}
	}

	private void sepsisCheck() {
		InfectionLookup iL = new InfectionLookup();
		System.out.println("Sepsis Check: " + getSubject_id());
		if (iL.getInfections().contains(getSubject_id())) {
			setSepsisFlag(true);
		} else {
			setSepsisFlag(false);
		}
	}

	private void severeCheck() {
		if (!(value1.isEmpty() && value1num.isEmpty())) {
			if (!(value1.equalsIgnoreCase("no data") || value1num.equalsIgnoreCase("no data") || 
					value1.equalsIgnoreCase("error") || value1num.equalsIgnoreCase("error") || 
					value1.equalsIgnoreCase("") || value1num.equalsIgnoreCase(""))) {
				if (label.equalsIgnoreCase("Arterial BP")) {
					if (new Double(value1).doubleValue() < 90 || new Double(value1num).doubleValue() < 90) {
						setSevereFlag(true);
					}
				} else if (label.startsWith("Lactic Acid")) {
					if (new Double(value1).doubleValue() > 2 || new Double(value1num).doubleValue() > 2) {
						setSevereFlag(true);
					}
				} else if (label.endsWith("pH") || label.startsWith("pH")) {
					if (new Double(value1).doubleValue() < 7.36 || new Double(value1num).doubleValue() < 7.36) {
						setSevereFlag(true);
					}
				} 
			} else {
				setSevereFlagNull(true);
			}
		} else {
			setSevereFlagNull(true);
		}

	}	
}
