package edu.jhu.icm.validator.model;

public class ShockTableLine extends SevereTableLine {

	private boolean fluid, Shock;
	private boolean fluidNull;
	private double lineFluid, fluidTotal;
	
	public ShockTableLine() {
		
		super();
		setFluid(false);
		setShock(false);
		setFluidNull(false);
		setFluidTotal(0);
		
	}

	
	public boolean isFluid() {
		return fluid;
	}


	public void setFluid(boolean fluid) {
		this.fluid = fluid;
	}


	public boolean isShock() {
		checkShock();
		return Shock;
	}


	public void setShock(boolean shock) {
		Shock = shock;
	}


	public boolean isFluidNull() {
		return fluidNull;
	}


	public void setFluidNull(boolean fluidNull) {
		this.fluidNull = fluidNull;
	}


	public double getLineFluid() {
		return lineFluid;
	}


	public void setLineFluid(double lineFluid) {
		this.lineFluid = lineFluid;
	}


	public double getFluidTotal() {
		return fluidTotal;
	}


	public void setFluidTotal(double fluidTotal) {
		this.fluidTotal = fluidTotal;
	}


	private void checkShock() {
		
		int count = 0;
		if (isSevere()) count++;
		if (getFluidTotal() > 1200) count++;
		if (count > 1) {
			setShock(true);
			setSeverityLevel(4);
		}
		 
	}
}
