package com.teatime.game.util;

import java.util.LinkedList;
import java.util.List;

import com.teatime.game.view.ProvinceView;

public class GroupedTilesOrderedByAttribute {
	
	private List<ProvinceView> originalTiles = new LinkedList<ProvinceView>();
	private List<ProvinceView> untouchedTiles = new LinkedList<ProvinceView>();
	private int size;

	public GroupedTilesOrderedByAttribute(List<ProvinceView> tiles, int terrainAttributeIndex) {
		
		for(ProvinceView tile : tiles) {
			originalTiles.add(tile);
		}
		
		// Add tiles having attribute
		for(ProvinceView tile : tiles) {
			if(tile.getProvince().getTerrainAttribute(terrainAttributeIndex) == true) {
				untouchedTiles.add(tile);
			}
		}
		
		size = (int) Math.sqrt(tiles.size());
	}

	public List<List<ProvinceView>> evaluate() {

		List<List<ProvinceView>> groupsOfTiles = new LinkedList<List<ProvinceView>>(); 

		while(!untouchedTiles.isEmpty()) {

			ProvinceView tile = untouchedTiles.remove(0);
			List<ProvinceView> groupOfTiles = groupOfTilesSharingAttribute(tile);
			groupsOfTiles.add(groupOfTiles);
		}

		return groupsOfTiles;
	}

	public List<ProvinceView> groupOfTilesSharingAttribute(ProvinceView tile) {

		List<ProvinceView> tilesToVerify = new LinkedList<ProvinceView>();
		List<ProvinceView> groupOfTiles = new LinkedList<ProvinceView>();

		tilesToVerify.add(tile);

		while(!tilesToVerify.isEmpty()) {

			ProvinceView thisTile = tilesToVerify.remove(0);

			ProvinceView neighbourTile = findLeftProvinceView(thisTile.getProvince().getIndex());
			if(neighbourTile != null) {

				if(untouchedTiles.contains(neighbourTile) && !tilesToVerify.contains(neighbourTile)) {
					tilesToVerify.add(neighbourTile);		
				}
			}
			neighbourTile = findRightProvinceView(thisTile.getProvince().getIndex());
			if(neighbourTile != null) {
				
				if(untouchedTiles.contains(neighbourTile) && !tilesToVerify.contains(neighbourTile)) {
					tilesToVerify.add(neighbourTile);
				}
			}
			neighbourTile = findTopProvinceView(thisTile.getProvince().getIndex());
			if(neighbourTile != null) {
				if(untouchedTiles.contains(neighbourTile) && !tilesToVerify.contains(neighbourTile)) {
					
					tilesToVerify.add(neighbourTile);		
				}
			}
			neighbourTile = findBottomProvinceView(thisTile.getProvince().getIndex());
			if(neighbourTile != null) {
				if(untouchedTiles.contains(neighbourTile) && !tilesToVerify.contains(neighbourTile)) {
					tilesToVerify.add(neighbourTile);		
				}
			}

			untouchedTiles.remove(thisTile);
			groupOfTiles.add(thisTile);
		}

		return groupOfTiles;
	}

	public ProvinceView findLeftProvinceView(int index) {
		
		// Left edge
		if(index % size == 0) return null;
				
		return findProvinceView(index - 1);
		
	}

	public ProvinceView findRightProvinceView(int index) {
		
		// Right edge
		if(((index + 1) % size) == 0) return null;
		
		return findProvinceView(index + 1);
	}

	public ProvinceView findTopProvinceView(int index) {

		// Top edge
		if(index < size) return null;

		return findProvinceView(index - size);
	}

	public ProvinceView findBottomProvinceView(int index) {

		// Bottom edge
		if(index >= ((size - 1) * size)) return null;
		
		return findProvinceView(index + size);		
	}
	
	public ProvinceView findProvinceView(int index) {
		ProvinceView tile = originalTiles.get(index);
		
		if(tile.getProvince().getHasWater() == true) return tile;
		
		return null;		
	}
}
