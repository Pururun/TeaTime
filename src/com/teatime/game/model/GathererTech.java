package com.teatime.game.model;

public class GathererTech extends Tech {

	@Override
	public int getMaxSize() {
		return level + 1;
	}

}
