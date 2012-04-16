package com.teatime.game.model;

import java.util.List;

public class Hunter extends Craft {

	@Override
	public Craft createCraft() {
		return new Hunter();
	}

	@Override
	public void increaseExperience(Tribe tribe) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canPerformFoodCraft() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Food performFoodCraft(List<Human> humans, Tech tech) {
		// TODO Auto-generated method stub
		return null;
	}

}
