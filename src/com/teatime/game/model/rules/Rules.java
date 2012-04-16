package com.teatime.game.model.rules;

public class Rules {
	
	/* GLOBAL */
	public final static int turnsPerYear = 4;
	
	/* HUMAN */
	public final static int humanOldAge = 45;
	public final static int humanAdultAge = 15;
	public final static int humanAdultEat = 2;
	public final static int humanChildEat = 1;
	public final static int starvationLimit = 2;
	
	/* FOOD */
	public final static int foodAgeTurns = 3;
	
	public static int gatherFood(int size, int tech, int exp) {
		return size * tech * exp;
	}
	
	public static int hunt() {
		return 0;
		//TODO
	}

}
