package com.teatime.game.model;

public class Food {
	
	private int amount;
	//TODO
	private int type;
	
	private int age;
	
	public final static int TYPE_MEAT = 0;
	public final static int TYPE_GATHER_FOOD = 1;
	
	public Food (int amount, int type, int age) {
		this.amount = amount;
		this.type = type;
		this.age = age;
	}
	
	public void age() {
		age--;
	}
	
	public boolean isOld() {
		return age <= 0;
	}
	
	public int eat(int amount) {
		if ( amount < this.amount ) {
			this.amount -= amount;
			return amount;
		} else {
			int left = this.amount;
			this.amount = 0;
			return left;
		}
	}
	
	public int getAmount() {
		return amount;
	}
	
	@Override
	public String toString() {
		return "Food amount: " + amount + " age: " + age;
	}

}
