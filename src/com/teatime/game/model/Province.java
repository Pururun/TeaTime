package com.teatime.game.model;

import java.util.Random;

public class Province {
	
	private enum Terrain { grass, stone, water, wood }
	
	private boolean hasWater = false;
	
	private boolean[] terrainAttributes;
	
	private int index;

//	private enum Grass { meadow, steppe }
//	private enum GrassWater { swamp }
//	private enum Stone { rocky, mountain }
//	private enum Water { lake, river, sea }
//	private enum Wood { grove, forrest }
	
	public enum Ids {
	    OPEN(100), CLOSE(200);

	    private final int id;
	    Ids(int id) { this.id = id; }
	    public int getValue() { return id; }
	}
	
	private Animals animals;
	
	public Province(int index) {
		
		this.index = index;
		
		terrainAttributes = new boolean[Terrain.values().length];
		
		for(int i = 0; i < Terrain.values().length; i++) {
			terrainAttributes[i] = false;
		}
		
		generateWaterAttribute();
	}
	
	public int getIndex() {
		return index;
	}
	
	public boolean getTerrainAttribute(int index) {
		return terrainAttributes[index];
	}
	
	private void generateProvinceAttributes() {
		generateWaterAttribute();
	}

	private void generateWaterAttribute() {
		Random random = new Random();
		
		if(random.nextInt(3) == 0) {
			hasWater = true;
			terrainAttributes[2] = true;
		}
	}
	
	public boolean getHasWater() {
		return hasWater;
	}
}
