package edu.jhu.icm.validator.model;

public class SirsTableLine {

	private int severityLevel;
	private double tValue, hrValue, rrValue, paco2Value, wbcValue;
	private boolean Temp, HR, RR, PaCO2, WBC, Simultaneous;
	private boolean TempNull, HRNull, RRNull, PaCO2Null, WBCNull;
	
	public SirsTableLine() {
		
		setTValue(0);
		setHrValue(0);
		setRrValue(0);
		setWbcValue(0);
		setTemp(false);
		setHR(false);
		setRR(false);
		setWBC(false);
		setWBC(false);
		setSimultaneous(false);
		setSeverityLevel(0);
		setTempNull(false);
		setHRNull(false);
		setRRNull(false);
		setWBCNull(false);
		
	}

	public int getSeverityLevel() {
		return severityLevel;
	}

	public void setSeverityLevel(int severityLevel) {
		this.severityLevel = severityLevel;
	}

	public double getTValue() {
		return tValue;
	}

	public void setTValue(double tValue) {
		this.tValue = tValue;
	}

	public double getHrValue() {
		return hrValue;
	}

	public void setHrValue(double hrValue) {
		this.hrValue = hrValue;
	}

	public double getRrValue() {
		return rrValue;
	}

	public void setRrValue(double rrValue) {
		this.rrValue = rrValue;
	}

	public double getPaco2Value() {
		return paco2Value;
	}

	public void setPaco2Value(double paco2Value) {
		this.paco2Value = paco2Value;
	}

	public double getWbcValue() {
		return wbcValue;
	}

	public void setWbcValue(double wbcValue) {
		this.wbcValue = wbcValue;
	}

	public boolean isTemp() {
		return Temp;
	}

	public void setTemp(boolean temp) {
		Temp = temp;
	}

	public boolean isHR() {
		return HR;
	}

	public void setHR(boolean hR) {
		HR = hR;
	}

	public boolean isRR() {
		return RR;
	}

	public void setRR(boolean rR) {
		RR = rR;
	}

	public boolean isWBC() {
		return WBC;
	}

	public void setWBC(boolean wBC) {
		WBC = wBC;
	}

	public boolean isPaco2() {
		return PaCO2;
	}

	public void setPaco2(boolean Paco2) {
		PaCO2 = Paco2;
	}

	public boolean isSimultaneous() {
		checkSimultaneous();
		return Simultaneous;
	}

	public void setSimultaneous(boolean simultaneous) {
		Simultaneous = simultaneous;
	}
	
	public boolean isTempNull() {
		return TempNull;
	}

	public void setTempNull(boolean tempNull) {
		TempNull = tempNull;
	}

	public boolean isHRNull() {
		return HRNull;
	}

	public void setHRNull(boolean hRNull) {
		HRNull = hRNull;
	}

	public boolean isRRNull() {
		return RRNull;
	}

	public void setRRNull(boolean rRNull) {
		RRNull = rRNull;
	}

	public boolean isWBCNull() {
		return WBCNull;
	}

	public void setWBCNull(boolean wBCNull) {
		WBCNull = wBCNull;
	}

	public boolean isPaco2Null() {
		return PaCO2Null;
	}

	public void setPaco2Null(boolean Paco2Null) {
		PaCO2Null = Paco2Null;
	}

	private void checkSimultaneous() {
		
		int count = 0;
		if (isTemp()) count++;
		if (isHR()) count++;
		if (isRR()) count++;
		if (isWBC()) count++;
		if (count > 1) {
			setSimultaneous(true);
			setSeverityLevel(1);
		}
		
	}
}
