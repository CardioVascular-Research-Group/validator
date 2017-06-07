package edu.jhu.icm.validator.model;

public class Aggregation {

	private String subject_id, chartTime, hr, rr, paCO2, temp, wbc, infection, sbp, dbp, lacticAcid, fluidTotal;
	
	public Aggregation() {
		
		setSubject_id("");
		setChartTime("");
		setHr("");
		setRr("");
		setPaCO2("");
		setTemp("");
		setWbc("");
		setInfection("");
		setSbp("");
		setDbp("");
		setLacticAcid("");
		setFluidTotal("");
		
	}

	public String getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}

	public String getChartTime() {
		return chartTime;
	}

	public void setChartTime(String chartTime) {
		this.chartTime = chartTime;
	}

	public String getHr() {
		return hr;
	}

	public void setHr(String hr) {
		this.hr = hr;
	}

	public String getRr() {
		return rr;
	}

	public void setRr(String rr) {
		this.rr = rr;
	}

	public String getPaCO2() {
		return paCO2;
	}

	public void setPaCO2(String paCO2) {
		this.paCO2 = paCO2;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getWbc() {
		return wbc;
	}

	public void setWbc(String wbc) {
		this.wbc = wbc;
	}

	public String getInfection() {
		return infection;
	}

	public void setInfection(String infection) {
		this.infection = infection;
	}

	public String getSbp() {
		return sbp;
	}

	public void setSbp(String sbp) {
		this.sbp = sbp;
	}

	public String getDbp() {
		return dbp;
	}

	public void setDbp(String dbp) {
		this.dbp = dbp;
	}

	public String getLacticAcid() {
		return lacticAcid;
	}

	public void setLacticAcid(String lacticAcid) {
		this.lacticAcid = lacticAcid;
	}

	public String getFluidTotal() {
		return fluidTotal;
	}

	public void setFluidTotal(String fluidTotal) {
		this.fluidTotal = fluidTotal;
	}

	
}
