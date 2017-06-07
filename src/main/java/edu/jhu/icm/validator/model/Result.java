package edu.jhu.icm.validator.model;

public class Result extends Aggregation{

	private String severityLevel;
	
	public Result() {
	
		super();
		setSeverityLevel("");
		
	}

	public String getSeverityLevel() {
		return severityLevel;
	}

	public void setSeverityLevel(String severityLevel) {
		this.severityLevel = severityLevel;
	}
	
}
