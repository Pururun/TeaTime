package com.teatime.game.model.com;

import com.teatime.game.model.Craft;
import com.teatime.game.model.Food;
import com.teatime.game.model.Gatherer;
import com.teatime.game.model.Hunter;

public class Result {
	
	public int nrOfGatherers;
	public int nrOfHunters;
	
	public int foodGathered;
	public int foodHunted;
	
	public int nrOfStarvingHumans;
	
	public int nrOfBirths;
	public int nrOfDeaths;
	
	public double populationGrowth;
	
	public void addFoodCraftResult(Craft craft, Food food) {
		if ( craft instanceof Hunter ) {
			foodHunted = food.getAmount();
		} else if ( craft instanceof Gatherer ) {
			foodGathered = food.getAmount();
		}
	}

}
