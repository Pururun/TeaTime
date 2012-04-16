package com.teatime.game.model;

public abstract class Craft {
	
	private int experience;
	
	public abstract Craft createCraft ();
	
	public abstract void increaseExperience(Tribe tribe);

}
