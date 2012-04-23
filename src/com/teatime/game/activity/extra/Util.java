package com.teatime.game.activity.extra;

public class Util {
	
	public static double round (double in, int decimals) {
		
		 return Math.round(in*Math.pow(10,decimals))/Math.pow(10,decimals);
	}

}
