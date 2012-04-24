package com.teatime.game.model;

public class Terrain {
	
	private enum TerrainType { grass, stone, water, wood }

	private int terrainTypeId;
	
	public int getTerrainTypeId() {
		return terrainTypeId;
	}
	
	public Terrain(TerrainType terrainType) {
		this.terrainTypeId = terrainTypeToTerrainTypeId(terrainType);
	}
	
	protected int terrainTypeToTerrainTypeId(TerrainType terrainType) {
		return terrainType.ordinal();
	}
}
