package com.teatime.game.model;

public class HunterTech extends Tech {

	@Override
	public int getMaxSize() {
		return level + 1;
	}

}
