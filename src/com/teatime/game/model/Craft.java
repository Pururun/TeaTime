package com.teatime.game.model;

import java.util.List;

public abstract class Craft {
	
	private int experience;
	
	public abstract Craft createCraft();
	
	public abstract void increaseExperience(Tribe tribe);
	
	public abstract boolean canPerformFoodCraft();
	
	public abstract Food performFoodCraft(List<Human> humans, Tech tech);
	
	public int getExperience() {
		return experience;
	}

}
