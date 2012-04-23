package com.teatime.game.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.teatime.game.R;
import com.teatime.game.model.World;
import com.teatime.game.model.com.Orders;
import com.teatime.game.model.com.Result;

/**
 * 
 * Staplar
 * 
 * @author
 *
 */
public class AssignActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {
	
	
	public static int gathererAssign;
	public static int hunterAssign;
	
	private ProgressDialog progressDialog;
	private SeekBar huntersGatherersBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_assign);
        
        huntersGatherersBar = (SeekBar)findViewById(R.id.seekBar);
        huntersGatherersBar.setOnSeekBarChangeListener(this);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add("Next turn");

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if ( item.getTitle().equals("Next turn") ) {
			
			Orders order = new Orders();
			order.percentageGatherers = gathererAssign * 0.01;
			order.percentageHunters = hunterAssign * 0.01;
			
			Result results = new SimulateWorldTask().doInBackground(order);
			
			showResults(results);
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	protected void showProgressDialog(String title, String message) {
		if ( progressDialog == null ) {
			progressDialog = ProgressDialog.show(this, title, message);
		}
	}
	
	protected void dismissProgressDialog() {
		if ( progressDialog != null ) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
	
	private void showResults(Result results) {
		Dialog resultDialog = new Dialog(this);
		resultDialog.setTitle("Last round results");
		
		View resultView = getLayoutInflater().inflate(R.layout.dialog_result, new LinearLayout(this), false);
		
		//Set up data
		TextView gatherers = (TextView) resultView.findViewById(R.id.gatherers_data);
		gatherers.setText("" + results.nrOfGatherers);
		
		TextView hunters = (TextView) resultView.findViewById(R.id.hunters_data);
		hunters.setText("" + results.nrOfHunters);
		
		TextView foodFromGatherers = (TextView) resultView.findViewById(R.id.food_gathered_data);
		foodFromGatherers.setText("" + results.foodGathered);
		
		TextView foodFromHunters = (TextView) resultView.findViewById(R.id.food_hunted_data);
		foodFromHunters.setText("" + results.foodHunted);
		
		TextView starvingHumans = (TextView) resultView.findViewById(R.id.starving_population_data);
		starvingHumans.setText("" + results.nrOfStarvingHumans);
		
		TextView births = (TextView) resultView.findViewById(R.id.births_data);
		births.setText("" + results.nrOfBirths);
		
		TextView deaths = (TextView) resultView.findViewById(R.id.deaths_data);
		deaths.setText("" + results.nrOfDeaths);
		
		TextView popGrowth = (TextView) resultView.findViewById(R.id.population_growth_data);
		popGrowth.setText(results.populationGrowth + "%");
		
		resultDialog.setContentView(resultView);
		
		resultDialog.show();
	}
	
	private class SimulateWorldTask extends AsyncTask<Orders, Integer, Result> {

		@Override
		protected Result doInBackground(Orders... params) {
			World.getWorld().simulateWorld(params[0]);
			return World.getWorld().getResults("Player");
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgressDialog(null, "Simulating...");
		}
		
		@Override
		protected void onPostExecute(Result result) {
			super.onPostExecute(result);
			dismissProgressDialog();
		}
		
	}

	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {

		TextView nrOfHuntersText = (TextView)findViewById(R.id.nrOfHuntersText);
		TextView nrOfGatherersText = (TextView)findViewById(R.id.nrOfGatherersText);
		
		gathererAssign = progress;
		hunterAssign = 100 - progress;

		nrOfHuntersText.setText(String.valueOf(gathererAssign));
		nrOfGatherersText.setText(String.valueOf(hunterAssign));
	}

	public void onStartTrackingTouch(SeekBar seekBar) {}
	public void onStopTrackingTouch(SeekBar seekBar) {}
}
