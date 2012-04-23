package com.teatime.game.model;

public class Province {
	
	private enum Terrain { grass, stone, water, wood }
	
	private enum Grass { meadow, steppe }
	private enum GrassWater { swamp }
	private enum Stone { rocky, mountain }
	private enum Water { lake, river, sea }
	private enum Wood { grove, forrest }
	
	private Animals animals;

}
