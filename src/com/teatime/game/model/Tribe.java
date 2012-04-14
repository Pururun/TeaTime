package com.teatime.game.model;

import java.util.ArrayList;
import java.util.List;

public class Tribe implements Actor {
	
	private List<Human> humans;
	private List<Food> food;
	
	private List<Province> ownedProvinces;
	
	private Tech gathererTech;
	private Tech hunterTech;
	
	
	public Tribe(List<Province> provinces) {
		ownedProvinces = provinces;
		
		humans = new ArrayList<Human>();
		food = new ArrayList<Food>();
		
		gathererTech = new Tech();
		hunterTech = new Tech();
		
		generateHumans();
	}
	
	private void generateHumans() {
		
	}
	
	@Override
	public void simulateNext() {
		
	}

}
