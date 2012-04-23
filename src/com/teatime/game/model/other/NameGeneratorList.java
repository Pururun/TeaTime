package com.teatime.game.model.other;

import java.util.HashMap;
import java.util.Map;

import com.teatime.game.model.Tribe;

public class NameGeneratorList {
	
	private static Map<Tribe, NameGenerator> nameLists = new HashMap<Tribe, NameGenerator>();
	
	public static void assignGenerator(Tribe tribe) {
		nameLists.put(tribe, new NameGenerator());
	}
	
	public static NameGenerator getGenerator(Tribe tribe) {
		return nameLists.get(tribe);
	}

}
