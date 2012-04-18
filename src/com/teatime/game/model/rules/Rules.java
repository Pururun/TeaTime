package com.teatime.game.model.rules;

import java.util.Random;

public class Rules {
	
	/* GLOBAL */
	public final static int turnsPerYear = 4;
	
	/* HUMAN */
	public final static int humanOldAge = 65;
	public final static int humanAdultAge = 15;
	public final static int humanAdultEat = 3;
	public final static int humanChildEat = 1;
	public final static int starvationLimit = 2;
	
	/* CRAFTS */
	public final static double gathererValue = 1.1;
	public final static double hunterValue = 1.5;
	public final static double gathererIncrease = 0.03;
	public final static double hunterIncrease = 0.01;
	public final static double hunterLeadershipBonus = 6.0;
	public final static double gathererLeadershipBonus = 2.5;
	
	/* FOOD */
	public final static int foodAgeTurns = 3;
	
	public static int gatherFood(int size, int tech, double exp) {
		return (int) (size * tech * exp);
	}
	
	public static int hunt(int size, int tech, double exp) {
		Random rand = new Random();
		return (int) ((size + rand.nextInt(10)) * tech * exp);
	}

}
