package edu.jhu.icm.validator.model;

public class IoEvent {

	private String subject_id;
	private String icustay_id;
	private String charttime;
	private String label;
	private String elemid; 
	private String altid; 
	private String realtime;       
	private String cgid; 
	private String cuid; 
	private String volume;      
	private String volumeuom; 
	private String unitshung; 
	private String unitshunguom; 
	private String newbottle; 
	private String stopped; 
	private String estimate;
	
	public IoEvent() {
		setSubject_id("");
		setIcustay_id("");
		setCharttime("");
		setLabel("");
		setElemid("");
		setAltid("");
		setRealtime("");
		setCgid("");
		setCuid("");
		setVolume("");      
		setVolumeuom(""); 
		setUnitshung(""); 
		setUnitshunguom(""); 
		setNewbottle(""); 
		setStopped(""); 
		setEstimate("");
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
	public String getElemid() {
		return elemid;
	}
	public void setElemid(String elemid) {
		this.elemid = elemid;
	}
	public String getAltid() {
		return altid;
	}
	public void setAltid(String altid) {
		this.altid = altid;
	}
	public String getRealtime() {
		return realtime;
	}
	public void setRealtime(String realtime) {
		this.realtime = realtime;
	}
	public String getCgid() {
		return cgid;
	}
	public void setCgid(String cgid) {
		this.cgid = cgid;
	}
	public String getCuid() {
		return cuid;
	}
	public void setCuid(String cuid) {
		this.cuid = cuid;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getVolumeuom() {
		return volumeuom;
	}
	public void setVolumeuom(String volumeuom) {
		this.volumeuom = volumeuom;
	}
	public String getUnitshung() {
		return unitshung;
	}
	public void setUnitshung(String unitshung) {
		this.unitshung = unitshung;
	}
	public String getUnitshunguom() {
		return unitshunguom;
	}
	public void setUnitshunguom(String unitshunguom) {
		this.unitshunguom = unitshunguom;
	}
	public String getNewbottle() {
		return newbottle;
	}
	public void setNewbottle(String newbottle) {
		this.newbottle = newbottle;
	}
	public String getStopped() {
		return stopped;
	}
	public void setStopped(String stopped) {
		this.stopped = stopped;
	}
	public String getEstimate() {
		return estimate;
	}
	public void setEstimate(String estimate) {
		this.estimate = estimate;
	} 
		
}
