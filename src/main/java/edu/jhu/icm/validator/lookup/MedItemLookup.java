package edu.jhu.icm.validator.lookup;

import java.util.HashMap;

public class MedItemLookup {

	private HashMap<String,String> medItems = new HashMap<String,String>();
	
	public MedItemLookup() {
		
		HashMap<String,String> temp = new HashMap<String,String>();
		temp.put("42","Dobutamine");
		temp.put("43","Dopamine");
		temp.put("44","Epinephrine");
		temp.put("47","Levophed");
		temp.put("119","Epinephrine-k");
		temp.put("120","Levophed-k");
		temp.put("127","Neosynephrine");
		temp.put("128","Neosynephrine-k");
		temp.put("306","Dobutamine Drip");
		temp.put("307","Dopamine Drip");
		temp.put("309","Epinephrine Drip");
		setMedItems(temp);
	}

	public HashMap<String, String> getMedItems() {
		return medItems;
	}

	public void setMedItems(HashMap<String, String> medItems) {
		this.medItems = medItems;
	}
	
}
