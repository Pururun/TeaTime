package com.teatime.game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	
	public void simulateNext(Object data) {
		
		Orders orders = (Orders) data;
		
		//Set up humans and their tasks
		setUpAssignments(orders);
		
		//Perform tasks
		performTasks();
		
		//Feed people
		feedHumans();
		
		//Age food and remove old food
		for ( Food f : food ) {
			f.age();
		}
		
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
		int newChildren = getNumberOfNewChildren();
		Collections.sort(humans, new PregnantSorter());
		for ( int i = 0; i < newChildren; i++ ) {
			Human h = humans.get(i);
			if ( !h.canBePregnant() ) {
				break;
			}
		}
		
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
	
	private void feedHumans() {
		Collections.sort(humans, new FoodSorter());
		
		for ( Human h : humans ) {
			Food eatableFood = getFirstNonEmptyFood();
			if ( eatableFood == null ) {
				break;
			} else {
				while ( !h.hasEatenFull() ) {
					h.eat(eatableFood);
				}
			}
		}
	}
	
	private Food getFirstNonEmptyFood() {
		Iterator<Food> it = food.iterator();
		while ( it.hasNext() ) {
			Food f = it.next();
			if ( f.getAmount() <= 0 ) {
				it.remove();
			} else {
				return f;
			}
		}
		
		return null;
	}
	
	private int getNumberOfNewChildren() {
		return getStoredFood()/6;
	}
	
	public void addHuman (Human human) {
		humans.add(human);
	}
	
	public int getNumberOfAssignableHumans() {
		return getListofAssignableHumans().size();
	}
	
	public int getStoredFood() {
		int storedFood = 0;
		for ( Food f : food ) {
			if ( !f.isOld() ) {
				storedFood += f.getAmount();
			}
		}
		
		return storedFood;
	}
	
	private class PregnantSorter implements Comparator<Human> {

		@Override
		public int compare(Human lhs, Human rhs) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
	

}
