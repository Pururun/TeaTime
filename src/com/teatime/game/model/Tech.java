package com.teatime.game.model;

import com.teatime.game.model.rules.Rules;

public abstract class Tech {
	
	protected int level;
	
	protected int progress;
	
	public Tech() {
		progress = 0;
		level = 0;
	}
	
	public abstract int getMaxSize();
	
	public void tryLevel() {
		if ( progress > Rules.progressPerLevel ) {
			level++;
			progress = 0;
		}
	}
	
	public int getLevel() {
		return level;
	}

}
