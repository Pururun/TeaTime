package com.teatime.game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.teatime.game.model.com.Orders;
import com.teatime.game.model.rules.Rules;

public class Tribe implements Actor {
	
	private List<Human> humans;
	private List<Food> food;
	
	private List<Province> ownedProvinces;
	
	private List<Tech> techs;
	
	
	public Tribe(List<Province> provinces) {
		ownedProvinces = provinces;
		
		humans = new ArrayList<Human>();
		food = new ArrayList<Food>();
		
		techs = new ArrayList<Tech>();
		techs.add(new GathererTech());
		techs.add(new HunterTech());
		
		//Start up tribe
		generateHumans();
	}
	
	private void generateHumans() {
		
	}
	
	@Override
	public void simulateNext(Object data) {
		
		Orders orders = (Orders) data;
		
		//Set up humans and their tasks
		setUpAssignments(orders);
		
		//Perform tasks
		performTasks();
		
		//Feed people
		
		//Simluate next
		for ( Human h : humans ) {
			h.simulateNext(this);
		}
		
		//Clear dead people
		Iterator<Human> it = humans.iterator();
		while ( it.hasNext() ) {
			Human h = it.next();
			if ( !h.isAlive() ) {
				it.remove();
			}
		}
		
		//If possible, make people pregnant
		
	}
	
	private void setUpAssignments(Orders orders) {
		double percentageHunters = orders.percentageHunters;
		double percentageGatherers = orders.percentageGatherers;
		
		List<Human> assignables = getListofAssignableHumans();
	}
	
	private List<Human> getListofAssignableHumans() {
		List<Human> assignables = new ArrayList<Human>();
		
		for ( Human h : humans ) {
			if ( h.getAge() >= Rules.humanAdultAge && !h.isPregnant() ) {
				assignables.add(h);
			}
		}
		
		return assignables;
	}
	
	private void performTasks() {
		
		Map<Craft, List<Human>> craftsMap = calculateCraft();
		Set<Craft> crafts = craftsMap.keySet();
		
		for ( Craft craft : crafts ) {
			if ( craft.canPerformFoodCraft() ) {
				food.add( craft.performFoodCraft(craftsMap.get(craft), getTech(craft)) );
			}
		}
		
	}
	
	public Map<Craft, List<Human>> calculateCraft() {
		
		Map<Craft, List<Human>> crafts = new HashMap<Craft, List<Human>>();
		
		for ( Human h : humans ) {
			if ( h.getAge() >= Rules.humanAdultAge && !h.isPregnant() ) {
				if ( crafts.containsKey(h.getCurrentCraft()) ) {
					List<Human> crafters = crafts.get(h.getCurrentCraft());
					crafters.add(h);
					crafts.put(h.getCurrentCraft(), crafters);
				} else {
					List<Human> crafters = new ArrayList<Human>();
					crafters.add(h);
					crafts.put(h.getCurrentCraft(), crafters);
				}
			}
		}
		
		return crafts;
	}
	
	private Tech getTech ( Craft craft ) {
		if ( craft instanceof Gatherer ) {
			return techs.get(0);
		} else if ( craft instanceof Hunter ) {
			return techs.get(1);
		} else {
			return null;
		}
	}
	
	public void addHuman (Human human) {
		humans.add(human);
	}
	
	public int getNumberOfAssignableHumans() {
		return getListofAssignableHumans().size();
	}
	

}
