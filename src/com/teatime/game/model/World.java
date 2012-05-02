package com.teatime.game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.teatime.game.model.com.Orders;
import com.teatime.game.model.com.Result;

public class World {
	
	private List<Tribe> tribes;
	private List<Province> provinces;
	private static World instance;
	
	private World (List<Tribe> tribes, List<Province> provinces) {
		this.tribes = tribes;
		this.provinces = provinces;
	}
	
	/*
	 * The world will consist of size * size provinces
	 * and a tribe put randomly at one of the provinces
	 */
	public static World createWorld(int size) {
		
		// Provinces
		List<Province> newProvinces = new ArrayList<Province>();
		int nrOfProvinces = size * size;
		
		Province newProvince;
		for(int i = 0; i < nrOfProvinces; i++) {
			newProvince = new Province(i);
			newProvinces.add(newProvince);
		}
		
		// Tribes
		List<Tribe> newTribes = new ArrayList<Tribe>();
		
		Random r = new Random();
		int homeProvinceIndex = r.nextInt(size);
		Province homeProvince = newProvinces.get(homeProvinceIndex);
		
		// Create a single tribe
		Tribe newTribe = new Tribe(newProvinces, homeProvince, "Player");
		newTribes.add(newTribe);
		
		instance = new World(newTribes, newProvinces);
		return instance;
	}
	
	public static World getWorld() {
		if (instance != null) {
			return instance;
		} else {
			//TODO Raise exception
			return null;
		}
	}
	
	public List<Province> getProvinces() {
		return provinces;
	}
	
	public List<Tribe> getTribes() {
		return tribes;
	}
	
	public void simulateWorld(Orders orders) {
		for ( Tribe t : tribes) {
			t.simulateNext(orders);
		}
	}
	
	public Tribe getTribe(String name) {
		for ( Tribe t : tribes ) {
			if ( t.getName().equals(name) ) {
				return t;
			}
		}
		return null;
	}
	
	public Result getResults(String name) {
		return getTribe(name).getResults();
	}
	
	public static List<Province> getOwnedProvinces(List<Province> worldProvinces, Tribe tribe) {
		int homeProvIndex = worldProvinces.indexOf(tribe.getHomeProvince());
		
		int range = tribe.getRange();
		
		List<Province> ownedProvinces = new ArrayList<Province>();
		
		//Add other provinces
		int upperLeft = (int) (homeProvIndex - Math.sqrt(worldProvinces.size())*range - range);
		int upperRight = (int) (homeProvIndex - Math.sqrt(worldProvinces.size())*range + range);
		int lowerLeft = (int) (homeProvIndex + Math.sqrt(worldProvinces.size())*range - range);
		int lowerRight = (int) (homeProvIndex + Math.sqrt(worldProvinces.size())*range + range);
		for ( int i = upperLeft; i <= lowerLeft; i+=Math.sqrt(worldProvinces.size()) ) {
			for (int j = i; j <= i + (upperRight - upperLeft); j++) {
				try {
					ownedProvinces.add(worldProvinces.get(j));
				} catch (ArrayIndexOutOfBoundsException e) {
					
				}
				if ( (j+1) % Math.sqrt(worldProvinces.size()) == 0 ) {
					j = i + (upperRight - upperLeft);
				}
			}
		}
		
		return ownedProvinces;
	}

}
