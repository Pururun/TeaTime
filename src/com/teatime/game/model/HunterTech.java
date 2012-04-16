package com.teatime.game.model;

public class HunterTech extends Tech {

	@Override
	public int getSkill() {
		return level + 1;
	}

}
