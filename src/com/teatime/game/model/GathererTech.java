package com.teatime.game.model;

public class GathererTech extends Tech {

	@Override
	public int getSkill() {
		return level + 1;
	}

}
