package edu.jhu.icm.validator.model;

public class SepsisTableLine extends SirsTableLine {

	private boolean infection, sepsis;

	public SepsisTableLine() {

		super();
		setInfection(false);

	}

	public boolean isInfection() {
		return infection;
	}

	public void setInfection(boolean infection) {
		this.infection = infection;
	}

	public boolean isSepsis() {
		checkSepsis();
		return sepsis;
	}

	public void setSepsis(boolean sepsis) {
		this.sepsis = sepsis;
	}

	private void checkSepsis() {

		int count = 0;
		if (isSimultaneous()) count++;
		if (isInfection()) count++;
		if (count > 1) {
			setSepsis(true);
			setSeverityLevel(2);
		}

	}
	
}
