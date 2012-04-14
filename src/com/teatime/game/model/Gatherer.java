package com.teatime.game.model;

public class Gatherer extends Craft {

	@Override
	public Craft createCraft() {
		return new Gatherer();
	}

}
