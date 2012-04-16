package com.teatime.game.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	
	public void simulateNext(Object data) {
		
		Orders orders = (Orders) data;
		
		//Set up humans and their tasks
		setUpAssignments(orders);
		
		//Perform tasks
		
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
	
	public void addHuman (Human human) {
		humans.add(human);
	}
	
	public int getNumberOfAssignableHumans() {
		return getListofAssignableHumans().size();
	}

}
