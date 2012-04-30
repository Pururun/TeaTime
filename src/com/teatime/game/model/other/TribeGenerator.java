package com.teatime.game.model.other;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.teatime.game.model.Craft;
import com.teatime.game.model.Gatherer;
import com.teatime.game.model.Human;
import com.teatime.game.model.Human.Sex;
import com.teatime.game.model.Hunter;
import com.teatime.game.model.rules.Rules;

public class TribeGenerator {
	
	private static final int CHILD = 0;
	private static final int YOUNG = 1;
	private static final int NORMAL = 2;
	private static final int OLD = 3;
	
	private static final double childPercentage = 0.1;
	private static final double youngPercentage = 0.2;
	private static final double normalPercentage = 0.5;
	private static final double oldPercentage = 0.2;
	
	public static List<Human> generateTribe(int size) {
		
		List<Human> humans = new ArrayList<Human>();
		
		int noOfChildren = (int) (size*childPercentage);
		int noOfYoung = (int) (size*youngPercentage);
		int noOfAdults = (int) (size*normalPercentage);
		int noOfOldHumans = (int) (size*oldPercentage);
		
		if ( (noOfChildren + noOfYoung + noOfAdults + noOfOldHumans) < size ) {
			noOfAdults += size - (noOfChildren + noOfYoung + noOfAdults + noOfOldHumans);
		}
		
		while ( noOfChildren > 0 ) {
			humans.add(generateHuman(CHILD, null));
			noOfChildren--;
		}
		
		while ( noOfYoung > 0 ) {
			Random rand = new Random();
			if ( rand.nextInt(2) > 0 ) {
				humans.add(generateHuman(YOUNG, new Hunter()));
			} else {
				humans.add(generateHuman(YOUNG, new Gatherer()));
			}
			noOfYoung--;
		}
		
		while ( noOfAdults > 0 ) {
			Random rand = new Random();
			if ( rand.nextInt(2) > 0 ) {
				humans.add(generateHuman(NORMAL, new Hunter()));
			} else {
				humans.add(generateHuman(NORMAL, new Gatherer()));
			}
			noOfAdults--;
		}
		
		while ( noOfOldHumans > 0 ) {
			Random rand = new Random();
			if ( rand.nextInt(2) > 0 ) {
				humans.add(generateHuman(OLD, new Hunter()));
			} else {
				humans.add(generateHuman(OLD, new Gatherer()));
			}
			noOfOldHumans--;
		}
		
		return humans;
	}
	
	public static Human generateHuman(int ageSpan, Craft craft) {
		
		int age = generateAge(ageSpan);
		
		Human.Sex sex;
		Random rand = new Random();
		if ( rand.nextInt(2) > 0 ) {
			sex = Sex.Male;
		} else {
			sex = Sex.Female;
		}
		
		Human newHuman = new Human(age, sex);
		
		//Assign experience
		newHuman.assignCraft(craft);
		while ( age >= Rules.humanAdultAge ) {
			newHuman.getCurrentCraft().increaseExperience(null);
			age--;
		}
		
		return newHuman;
		
	}
	
	private static int generateAge(int ageSpan) {
		Random rand = new Random();
		switch(ageSpan) {
		case CHILD:
			return rand.nextInt(Rules.humanAdultAge - 5);
		case YOUNG:
			return rand.nextInt(Rules.humanAdultAge) + (Rules.humanAdultAge - 5);
		case NORMAL:
			return rand.nextInt( Rules.humanFertilityLimitAge - (Rules.humanAdultAge + (Rules.humanAdultAge - 5))) + Rules.humanAdultAge + (Rules.humanAdultAge - 5);
		case OLD:
			return rand.nextInt(Rules.humanOldAge - Rules.humanFertilityLimitAge) + Rules.humanFertilityLimitAge;
		}
		
		return -1;
	}

}
