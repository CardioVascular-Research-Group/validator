package edu.jhu.icm.validator.model;

public class MedEvent {

	private String subject_id;
	private String icustay_id;
	private String charttime;
	private String label;
	private String volume; 
	private String dose;  
	private String doseuom; 
	private String solutionid; 
	private String solvolume; 
	private String solunits; 
	private String route;

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
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getDose() {
		return dose;
	}
	public void setDose(String dose) {
		this.dose = dose;
	}
	public String getDoseuom() {
		return doseuom;
	}
	public void setDoseuom(String doseuom) {
		this.doseuom = doseuom;
	}
	public String getSolutionid() {
		return solutionid;
	}
	public void setSolutionid(String solutionid) {
		this.solutionid = solutionid;
	}
	public String getSolvolume() {
		return solvolume;
	}
	public void setSolvolume(String solvolume) {
		this.solvolume = solvolume;
	}
	public String getSolunits() {
		return solunits;
	}
	public void setSolunits(String solunits) {
		this.solunits = solunits;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}

	
}
