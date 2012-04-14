package com.teatime.game.model;

public class Hunter extends Craft {

	@Override
	public Craft createCraft() {
		return new Hunter();
	}

}
