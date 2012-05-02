package com.teatime.game.model;

import java.util.List;

import com.teatime.game.model.rules.Rules;

public class Hunter extends Craft {

	@Override
	public Craft createCraft() {
		return new Hunter();
	}

	@Override
	public void increaseExperience(Tribe tribe) {
		experience+=Rules.hunterIncrease;
	}

	@Override
	public boolean canPerformFoodCraft() {
		return true;
	}

	@Override
	public Food performFoodCraft(List<Human> humans, Tech tech) {
		// Calculate size
		int size = humans.size();

		// Calculate experience and leadership bonus
		double exp = 0.0;
		double bestExp = 0.0;
		for (Human h : humans) {
			exp += h.getCurrentCraft().getExperience();
			bestExp = Math.max(bestExp, h.getCurrentCraft().getExperience());
			h.getCurrentCraft().increaseExperience(null);
		}
		
		//Add leadership bonus
		exp += (bestExp * Rules.hunterLeadershipBonus);

		// Increase tech
		tech.progress += exp;
		
		return new Food(Rules.hunt(size, exp), Food.TYPE_MEAT,
				Rules.foodAgeTurns);
	}
	
	@Override
	public Food performFoodCraft(List<Human> humans, Tech tech,
			Province province) {
		// Calculate size
		int size = humans.size();

		// Calculate experience and leadership bonus
		double exp = 0.0;
		double bestExp = 0.0;
		for (Human h : humans) {
			exp += h.getCurrentCraft().getExperience();
			bestExp = Math.max(bestExp, h.getCurrentCraft().getExperience());
			h.getCurrentCraft().increaseExperience(null);
		}

		// Add leadership bonus
		exp += (bestExp * Rules.hunterLeadershipBonus);

		// Increase tech
		tech.progress += exp;

		return new Food(Rules.hunt(size, exp, province.getAnimals().getMaxFood()), Food.TYPE_MEAT,
				Rules.foodAgeTurns);
	}

	@Override
	public double getValue() {
		return Rules.hunterValue;
	}
	
	@Override
	public String toString() {
		return "Hunter";
	}

}
