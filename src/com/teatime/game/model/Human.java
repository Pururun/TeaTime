package com.teatime.game.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.teatime.game.model.rules.Rules;

public class Human implements Actor {
	
	public enum Sex { Male, Female};
	
	//Note that internal and actual age is not the same...
	private int age;
	private Sex sex;  
	private Craft currentCraft = null;
	private Set<Craft> oldCrafts;
	private boolean isPregnant;
	private int starvation = 0;
	private boolean isAlive;
	private int foodToEat = 0;
	
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
		isAlive = true;
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
		
		isAlive = true;
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
	
	public void makePregnant() {
		if ( sex == Sex.Female ) {
			isPregnant = true;
		}
	}
	
	public boolean isPregnant() {
		return isPregnant;
	}
	
	public int getAge() {
		return age/Rules.turnsPerYear;
	}

	public void simulateNext(Object data) {
		
		Tribe tribe = (Tribe) data;
		
		age++;
		
		//Check for starvation
		if ( !hasEatenFull() ) {
			starvation++;
		}
		
		if ( starvation >= Rules.starvationLimit ) {
			this.kill();
		}
		
		//Check for old age
		if ( getAge() >= Rules.humanOldAge ) {
			this.kill();
		}
		
		//Make hungry again
		if ( getAge() >= Rules.humanAdultAge ) {
			foodToEat = Rules.humanAdultEat;
		} else {
			foodToEat = Rules.humanChildEat;
		}
		
		//Give birth
		if ( isPregnant() ) {
			tribe.addHuman(new Human());
		}
	}
	
	public boolean eat(Food food) {	
		
		foodToEat -= food.eat(foodToEat);
		
		return hasEatenFull();
	}
	
	public boolean hasEatenFull() {
		return foodToEat <= 0;
	}
	
	public void kill() {
		isAlive = false;
		currentCraft = null;
		oldCrafts = null;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public Craft getCurrentCraft() {
		return currentCraft;
	}
	

}
