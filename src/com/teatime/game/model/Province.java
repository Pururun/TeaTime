package com.teatime.game.model;

import java.util.Random;

public class Province {
	
	private enum Terrain { grass, stone, water, wood }

	private enum Grass { meadow, steppe }
	private enum GrassWater { swamp }
	private enum Stone { rocky, mountain }
	private enum Water { lake, river, sea }
	private enum Wood { grove, forrest }
	
	public enum Ids {
	    OPEN(100), CLOSE(200);

	    private final int id;
	    Ids(int id) { this.id = id; }
	    public int getValue() { return id; }
	}
	
	private Animals animals;
	
	public Province generateProvince() {
		
		int nrOfTerrainTypes = Terrain.values().length; 
		
		for(int i = 0; i < nrOfTerrainTypes; i++) {
			
			Random random = new Random(nrOfTerrainTypes);
			int dice = random.nextInt();
		}
		
		
		
		
		return null;
	}

}
