package com.teatime.game.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.teatime.game.R;
import com.teatime.game.model.World;

public class GameMainActivity extends TabActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_game_main);

	    // Create TabView
	    Resources res = getResources();
	    TabHost tabHost = getTabHost();
	    TabHost.TabSpec spec;
	    Intent intent;

	    intent = new Intent().setClass(this, MapActivity.class);

	    spec = tabHost.newTabSpec("Map").setIndicator("Map",
	                      res.getDrawable(R.drawable.tab_game_main))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, StatsViewActivity.class);
	    intent.putExtra("tribeName", "Player");
	    spec = tabHost.newTabSpec("Stats").setIndicator("Stats",
	                      res.getDrawable(R.drawable.tab_game_main))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, AssignActivity.class);
	    spec = tabHost.newTabSpec("Assign").setIndicator("Assign",
	                      res.getDrawable(R.drawable.tab_game_main))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(1);
	    
	    
	    // Create world
	    World.createWorld(16);
	}
}
