package com.teatime.game.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.teatime.game.R;

public class GameMainActivity extends TabActivity {
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_game_main);

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
	}
}
