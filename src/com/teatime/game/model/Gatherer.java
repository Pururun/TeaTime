package com.teatime.game.model;

import java.util.List;

import com.teatime.game.model.rules.Rules;

public class Gatherer extends Craft {

	@Override
	public Craft createCraft() {
		return new Gatherer();
	}

	@Override
	public void increaseExperience(Tribe tribe) {
		experience++;
	}

	@Override
	public boolean canPerformFoodCraft() {
		return true;
	}

	@Override
	public Food performFoodCraft(List<Human> humans, Tech tech) {
		
		//Calculate size
		int size = humans.size();
		
		//Calculate experience
		int exp = 0;
		for ( Human h : humans ) {
			exp += h.getCurrentCraft().getExperience();
			h.getCurrentCraft().increaseExperience(null);
		}
		
		//Increase tech
		tech.progress += exp;
		
		return new Food(Rules.gatherFood(size, exp, tech.getSkill()), Food.TYPE_GATHER_FOOD, Rules.foodAgeTurns);
	}

	@Override
	public double getValue() {
		return Rules.gathererValue;
	}

}
