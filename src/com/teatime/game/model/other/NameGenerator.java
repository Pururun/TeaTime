package com.teatime.game.model.other;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.teatime.game.model.Human;

public class NameGenerator {
	
	public final static String[] femaleNames = new String[] { "Star", "Garnet", "Strawberry", "Hummingbird", "Sara", "Petra", "Moon", "Moonchild", "Nightflower", "Dayflower", "Daisy", "Mayflower", "Tomato", "Cucumber", "Maer", "Dilba", "Jennie", "Tear", "Starfall", "Vienna", "Vilma", "Sunset", "Maria", "Eve", "Hen", "Birgitte", "Selma" };
	public final static String[] maleNames = new String[] { "Lion", "Sun", "Tiger", "Sabertooth", "Julius", "Stone", "Rock", "Spear", "Great Spear", "Shadow", "Light", "Phoenix", "Fire", "Club", "Bear", "Great Bear", "Little Bear", "Jonatan", "Matthias", "Wind", "Storm", "Thunder", "Meat", "Hubbel", "Alexander", "William", "Adam", "Beard", "Great Beard" };
	
	private List<String> availableFemaleNames;
	private List<String> usedFemaleNames;
	private List<String> availableMaleNames;
	private List<String> usedMaleNames;
	
	public NameGenerator(String[] femaleNames, String[] maleNames) {
		availableFemaleNames = new ArrayList<String>();	
		for (int i = 0; i < femaleNames.length; i++) {
			availableFemaleNames.add(femaleNames[i]);
		}			
		
		usedFemaleNames = new ArrayList<String>();
		
		availableMaleNames = new ArrayList<String>();	
		for (int i = 0; i < maleNames.length; i++) {
			availableMaleNames.add(maleNames[i]);
		}			
		
		usedMaleNames = new ArrayList<String>();
	}
	
	public NameGenerator() {
		
		availableFemaleNames = new ArrayList<String>();	
		for (int i = 0; i < femaleNames.length; i++) {
			availableFemaleNames.add(femaleNames[i]);
		}			
		
		usedFemaleNames = new ArrayList<String>();
		
		availableMaleNames = new ArrayList<String>();	
		for (int i = 0; i < maleNames.length; i++) {
			availableMaleNames.add(maleNames[i]);
		}			
		
		usedMaleNames = new ArrayList<String>();
		
	}
	
	public String generateName(Human.Sex sex) {
		
		List<String> available;
		List<String> used;
		
		if ( sex == Human.Sex.Female ) {
			available = availableFemaleNames;
			used = usedFemaleNames;
		} else {
			available = availableMaleNames;
			used = usedMaleNames;
		}
		
		if ( available.size() < 1 ) {
			available.addAll(used);
			used.clear();
		}
		
		Random rand = new Random();
		int randIndex = rand.nextInt(available.size());
		
		return available.get(randIndex);
		
	}
	

}
