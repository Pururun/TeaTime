package com.teatime.game.model;

import java.util.List;

import android.util.Log;

import com.teatime.game.model.rules.Rules;

public class Gatherer extends Craft {

	@Override
	public Craft createCraft() {
		return new Gatherer();
	}

	@Override
	public void increaseExperience(Tribe tribe) {
		experience+=Rules.gathererIncrease;
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
		double exp = 0;
		double bestExp = 0;
		for ( Human h : humans ) {
			exp += h.getCurrentCraft().getExperience();
			bestExp = Math.max(bestExp, h.getCurrentCraft().getExperience());
			h.getCurrentCraft().increaseExperience(null);
		}
		
		//Add leadership bonus
		exp += (bestExp * Rules.gathererLeadershipBonus);
		
		//Increase tech
		tech.progress += exp;
		
		return new Food(Rules.gatherFood(size, tech.getSkill(), exp), Food.TYPE_GATHER_FOOD, Rules.foodAgeTurns);
	}

	@Override
	public double getValue() {
		return Rules.gathererValue;
	}

}
