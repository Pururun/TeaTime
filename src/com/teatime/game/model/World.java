package com.teatime.game.model;

import java.util.ArrayList;
import java.util.List;

import com.teatime.game.model.com.Orders;

public class World {
	
	private List<Tribe> tribes;
	private List<Province> provinces;
	private static World instance;
	
	private World (List<Tribe> tribes, List<Province> provinces) {
		this.tribes = tribes;
		this.provinces = provinces;
	}
	
	public static World createWorld() {
		
		//Generate provinces
		List<Province> newProvinces = new ArrayList<Province>();
		//newProvinces.add(object);
		
		//Generate tribes
		List<Tribe> newTribes = new ArrayList<Tribe>();
		//newTribes.add(arg0)
		
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
	
	public void simulateWorld(Orders orders) {
		for ( Tribe t : tribes) {
			t.simulateNext(orders);
		}
	}

}
