package edu.jhu.icm.validator.model;

public class SevereTableLine extends SepsisTableLine {

	private double sbpValue, dbpValue, lacticAcidValue;
	private boolean BP, pH, lacticAcid, Severe;
	private boolean BPNull, pHNull, lacticAcidNull;
	
	public SevereTableLine() {
		
		super();
		setSbpValue(0);
		setDbpValue(0);
		setLacticAcidValue(0);
		setBP(false);
		setpH(false);
		setLacticAcid(false);
		setSevere(false);
		setBPNull(false);
		setpHNull(false);
		setLacticAcidNull(false);
		
	}

	public double getSbpValue() {
		return sbpValue;
	}

	public void setSbpValue(double sbpValue) {
		this.sbpValue = sbpValue;
	}

	public double getDbpValue() {
		return dbpValue;
	}

	public void setDbpValue(double dbpValue) {
		this.dbpValue = dbpValue;
	}

	public double getLacticAcidValue() {
		return lacticAcidValue;
	}

	public void setLacticAcidValue(double lacticAcidValue) {
		this.lacticAcidValue = lacticAcidValue;
	}

	public boolean isBP() {
		return BP;
	}

	public void setBP(boolean bP) {
		BP = bP;
	}

	public boolean ispH() {
		return pH;
	}

	public void setpH(boolean pH) {
		this.pH = pH;
	}

	public boolean isLacticAcid() {
		return lacticAcid;
	}

	public void setLacticAcid(boolean lacticAcid) {
		this.lacticAcid = lacticAcid;
	}

	public boolean isSevere() {
		checkSevere();
		return Severe;
	}

	public void setSevere(boolean severe) {
		Severe = severe;
	}
	
	public boolean isBPNull() {
		return BPNull;
	}

	public void setBPNull(boolean bPNull) {
		BPNull = bPNull;
	}

	public boolean ispHNull() {
		return pHNull;
	}

	public void setpHNull(boolean pHNull) {
		this.pHNull = pHNull;
	}

	public boolean isLacticAcidNull() {
		return lacticAcidNull;
	}

	public void setLacticAcidNull(boolean lacticAcidNull) {
		this.lacticAcidNull = lacticAcidNull;
	}

	private void checkSevere() {
		
		int count = 0;
		if (isSepsis()) count++;
		if (isBP()) count++;
		if (isLacticAcid()) count++;
		if (count > 1) {
			setSevere(true);
			setSeverityLevel(3);
		}
		 
	}
}
