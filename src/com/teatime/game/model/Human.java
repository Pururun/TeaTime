package com.teatime.game.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Human {
	
	public enum Sex { Male, Female};
	
	private int age;
	private Sex sex;  
	private Craft currentCraft = null;
	private Set<Craft> oldCrafts;
	private boolean isPregnant;
	
	/**
	 * 
	 * Used to generate humans at game start
	 * 
	 * @param age
	 * @param sex
	 */
	public Human (int age, Sex sex) {
		this.age = age;
		this.sex = sex;
		oldCrafts = new HashSet<Craft>();
	}
	
	/**
	 * 
	 * Used to create newborns.
	 * 
	 */
	public Human () {
		age = 0;
		Random rand = new Random();
		if ( rand.nextInt(1) > 0 ) {
			sex = Sex.Male;
		} else {
			sex = Sex.Female;
		}
		oldCrafts = new HashSet<Craft>();
	}
	
	public void assignCraft(Craft craft) {
		oldCrafts.add(currentCraft);
		currentCraft = null;

		for (Craft newCraft : oldCrafts) {
			if (newCraft.getClass() == craft.getClass()) {
				currentCraft = newCraft;
			}
		}
		
		if ( currentCraft == null ) {
			currentCraft = craft.createCraft();
		}
	}
	

}
