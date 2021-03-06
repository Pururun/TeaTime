package com.teatime.game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.util.Log;

import com.teatime.game.model.com.Orders;
import com.teatime.game.model.com.Result;
import com.teatime.game.model.other.NameGenerator;
import com.teatime.game.model.other.NameGeneratorList;
import com.teatime.game.model.other.TribeGenerator;
import com.teatime.game.model.rules.Rules;

public class Tribe implements Actor {
	
	private List<Human> humans;
	private List<Food> food;
	
	private List<Province> ownedProvinces;
	
	// Where the tribe resides
	private Province homeProvince;
	
	private List<Tech> techs;
	
	private final String name;
	
	private List<Human> newBorns;
	
	private Result lastRoundResults = null;
	
	public Tribe(List<Province> provinces, Province homeProvince, String name) {
		//ownedProvinces = provinces;
		this.homeProvince = homeProvince;
		
		humans = new ArrayList<Human>();
		food = new ArrayList<Food>();
		
		techs = new ArrayList<Tech>();
		techs.add(new GathererTech());
		techs.add(new HunterTech());
		
		this.name = name;
		
		//Assign namegenerator
		NameGeneratorList.assignGenerator(this);
		
		//Start up tribe
		generateHumans();
		
		//Assign ownedProvinces
		ownedProvinces = World.getOwnedProvinces(provinces, this);
	}
	
	private void generateHumans() {
		
		humans.addAll(TribeGenerator.generateTribe(30));
		
		//Assign names
		NameGenerator names = NameGeneratorList.getGenerator(this);
		for (Human h: humans) {
			h.setName(names.generateName(h.getSex()));
		}
		
	}
	
	public void simulateNext(Object data) {
		
		Orders orders = (Orders) data;
		
		//Clear old results
		lastRoundResults = new Result();
		
		//Set up humans and their tasks
		setUpAssignments(orders);
		
		//Perform tasks
		performTasks();
		
		//Feed people
		feedHumans();
		
		//Age food and remove old food
		Log.e("Clear food", "We are here");
		Iterator<Food> itFood = food.iterator();
		while ( itFood.hasNext() ) {
			Food f = itFood.next();
			f.age();
			if ( f.isOld() ) {
				itFood.remove();
			}
		}
		
		//Simluate next
		Log.e("Simulate next", "We are here");
		for ( Human h : humans ) {
			h.simulateNext(this);
		}
		
		//Add newborns to humans and assign names
		NameGenerator names = NameGeneratorList.getGenerator(this);
		lastRoundResults.nrOfBirths = newBorns != null ? newBorns.size() : 0;
		if ( newBorns != null ) {
			for ( Human newBorn : newBorns ) {
				newBorn.setName(names.generateName(newBorn.getSex()));
			}
			humans.addAll(newBorns);
			newBorns.clear();
		}
		
		//Clear dead people
		Iterator<Human> it = humans.iterator();
		while ( it.hasNext() ) {
			Human h = it.next();
			if ( !h.isAlive() ) {
				it.remove();
				lastRoundResults.nrOfDeaths++;
			}
		}
		
		//If possible, make people pregnant
		int newChildren = getNumberOfNewChildren();
		int actualPregnantWomen = 0;
		Log.e("Make Pregnant", "newChildren: " + newChildren + " population: " + humans.size());
		Collections.sort(humans, new PregnantSorter());
		for ( int i = 0; i < newChildren; i++ ) {
			Human h = humans.get(i);
			if ( !h.canBePregnant() ) {
				break;
			}
			h.makePregnant();
			actualPregnantWomen++;
		}
		
		lastRoundResults.populationGrowth = (actualPregnantWomen*1.0)/(humans.size()*1.0);
		
		//Update tech
		for ( Tech t : techs ) {
			t.tryLevel();		
		}
		
	}
	
	private void setUpAssignments(Orders orders) {
		List<Human> assignables = getListofAssignableHumans();
		
		int nrOfHunters = (int) (assignables.size()*orders.percentageHunters);
		int nrOfGatherers = (int) (assignables.size()*orders.percentageHunters);
		
		Log.e("setUpAssignments: ", "hunters: " + nrOfHunters + " gatherers: " + nrOfGatherers);
		
		//Assign spares to the largest group
		if ( assignables.size() > (nrOfHunters + nrOfGatherers) ) {
			if ( nrOfHunters >= nrOfGatherers )  {
				nrOfHunters += (assignables.size() - (nrOfHunters + nrOfGatherers));
			} else {
				nrOfGatherers += (assignables.size() - (nrOfHunters + nrOfGatherers));
			}		 
		}
		
		//Save assigned numbers
		lastRoundResults.nrOfHunters = nrOfHunters;
		lastRoundResults.nrOfGatherers = nrOfGatherers;
		
		//Sort By Best Hunters
		//Assign best hunters
		Craft c = new Hunter();
		Collections.sort(assignables, new AssignmentSorter(c));
		Iterator<Human> it = assignables.iterator();
		while ( it.hasNext() && nrOfHunters > 0 ) {
			Human h = it.next();
			h.assignCraft(c);
			it.remove();
			nrOfHunters--;
		}
		
		//Sort By Best Gatherers
		//Assign best gatherer
		c = new Gatherer();
		Collections.sort(assignables, new AssignmentSorter(c));
		it = assignables.iterator();
		while ( it.hasNext() && nrOfGatherers > 0 ) {
			Human h = it.next();
			h.assignCraft(c);
			it.remove();
			nrOfGatherers--;
		}		
	}
	
	public Province getHomeProvince() {
		return homeProvince;
	}
	
	private List<Human> getListofAssignableHumans() {
		List<Human> assignables = new ArrayList<Human>();
		
		for ( Human h : humans ) {
			if ( h.canPerformTask() ) {
				assignables.add(h);
			}
		}
		
		return assignables;
	}
	
	private void performTasks() {
		
		Map<Craft, List<Human>> craftsMap = calculateCraft();
		Set<Craft> crafts = craftsMap.keySet();
		
		Log.e("performTasks", "crafts: " + crafts.size() + " craftsMap.get()" + craftsMap.get(new Hunter()));
		
		for ( Craft craft : crafts ) {
			if ( craft.canPerformFoodCraft() ) {
				ProvinceSorter provSorter = new ProvinceSorter(craft);
				Collections.sort(ownedProvinces, provSorter);
				
				for ( int i = 0; i < ownedProvinces.size(); i++ ) {
					int maxSize = ownedProvinces.get(i).getMaxSize(craft, getTech(craft));
					List<Human> team = new ArrayList<Human>();
					while ( craftsMap.get(craft).size() > 0 && maxSize > 0 ) {
						team.add(craftsMap.get(craft).remove(0));
						maxSize--;
					}
					if (team.size() > 0) {
						Food gainedFood = craft.performFoodCraft(team, getTech(craft), ownedProvinces.get(i));
						lastRoundResults.addFoodCraftResult(craft, gainedFood, ownedProvinces.get(i));
						food.add( gainedFood );
					}
				}
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

		boolean starvation = false;
		Human starvingHuman = null;
		for (Human h : humans) {
			while (!h.hasEatenFull()) {
				Food eatableFood = getFirstNonEmptyFood();
				Log.e("feedHumans", "amount: " + eatableFood);
				if (eatableFood == null) {
					if ( !starvation ) {
						starvingHuman = h;
					}
					starvation = true;
					break;
				} else {
					h.eat(eatableFood);
				}
			}
		}
		
		if ( starvation && starvingHuman != null ) {
			lastRoundResults.nrOfStarvingHumans = humans.size() - humans.indexOf(starvingHuman);
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

		public int compare(Human lhs, Human rhs) {
			if ( lhs.getPregnantScore() > rhs.getPregnantScore() ) {
				return -1;
			} else if ( lhs.getPregnantScore() < rhs.getPregnantScore() ) {
				return 1;
			} else {
				return 0;
			}
		}
		
	}
	
	private class AssignmentSorter implements Comparator<Human> {
		
		Craft c;
		
		public AssignmentSorter(Craft c) {
			this.c = c;
		}

		public int compare(Human lhs, Human rhs) {
			if ( lhs.getCraftScore(c) > rhs.getCraftScore(c) ) {
				return -1;
			} else if ( lhs.getCraftScore(c) < rhs.getCraftScore(c) ) {
				return 1;
			} else {
				return 0;
			}
		}
		
	}
	
	public String getName() {
		return name;
	}
	
	public int getPopulation() {
		return humans.size();
	}
	
	public int getNumberOfAdults() {
		int adults = 0;
		for ( Human h : humans ) {
			if ( h.getAge() >= Rules.humanAdultAge ) {
				adults++;
			}
		}
		
		return adults;
	}
	
	public Tech getTech(Tech t) {
		for ( Tech tribeTech : techs ) {
			if ( tribeTech.getClass() == t.getClass() ) {
				return tribeTech;
			}
		}
		
		return null;
	}
	
	public void addNewBorn(Human human) {
		if ( newBorns == null ) {
			newBorns = new ArrayList<Human>();
		}
		
		newBorns.add(human);
	}
	
	public Result getResults() {
		return lastRoundResults;
	}
	
	public List<Human> getHumans() {
		return humans;
	}
	
	private class ProvinceSorter implements Comparator<Province> {

		Craft craft;
		
		public ProvinceSorter(Craft craft) {
			this.craft = craft;
		}
		
		@Override
		public int compare(Province arg0, Province arg1) {
			if ( arg0.getCraftScore(craft) > arg1.getCraftScore(craft) ) {
				return -1;
			} else if ( arg0.getCraftScore(craft) < arg1.getCraftScore(craft) ) {
				return 1;
			} else {
				return 0;
			}
		}
		
	}
	
	public int getRange() {
		//TODO Make this a real value
		return 1;
	}
}

