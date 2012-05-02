package com.teatime.game.model.rules;

import java.util.Random;

public class Rules {
	
	/* GLOBAL */
	public final static int turnsPerYear = 4;
	
	/* TECH */
	public final static int progressPerLevel = 400;
	
	/* HUMAN */
	public final static int humanOldAge = 65;
	public final static int humanFertilityLimitAge = 40;
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
	public final static int foodAgeTurns = 2;
	public final static int farmFoodAgeTurns = 5;
	
	public static int gatherFood(int size, double exp) {
		return (int) (size * exp);
	}
	
	public static int hunt(int size, double exp, int maxFood) {
		Random rand = new Random();
		return (int) ((size + rand.nextInt(maxFood)) * exp);
	}
	
	public static int hunt(int size, double exp) {
		Random rand = new Random();
		return (int) ((size + rand.nextInt(9)) * exp);
	}

}
