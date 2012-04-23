package com.teatime.game.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;

import com.teatime.game.R;
import com.teatime.game.activity.extra.HumanAdapter;
import com.teatime.game.model.Human;
import com.teatime.game.model.Tribe;
import com.teatime.game.model.World;

public class HumanListActivity extends Activity {
	
	private ListView list;
	private HumanAdapter adapter;
	
	private Tribe tribe;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_human_list);
		
		list = (ListView) findViewById(R.id.listView1);
		
		adapter = new HumanAdapter(this, R.layout.human_list_item);
		
		String tribeName = getIntent().getStringExtra("Tribe");
		
		if ( tribeName != null ) {
			tribe = World.getWorld().getTribe(tribeName);
		}
		
		list.setAdapter(adapter);
		
		for ( Human h : tribe.getHumans() ) {
			adapter.add(h);
		}
		
		adapter.notifyDataSetChanged();
		
	}

}
