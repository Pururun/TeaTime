package com.teatime.game.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.teatime.game.R;
import com.teatime.game.model.World;
import com.teatime.game.model.com.Orders;

/**
 * 
 * Staplar
 * 
 * @author 
 *
 */
public class AssignActivity extends BaseActivity {
	
	public static int gathererAssign;
	public static int hunterAssign;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_assign);
        
        EditText hunterAssignEdit = (EditText) findViewById(R.id.hunter_assign);
        EditText gathererAssignEdit = (EditText) findViewById(R.id.gatherer_assign);
        
        hunterAssignEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				try {
					hunterAssign = Integer.parseInt(arg0.toString());
				} catch ( Exception e) {
						
					}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        gathererAssignEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				try {
					gathererAssign = Integer.parseInt(arg0.toString());
				} catch (Exception e) {
					
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
        	
        });
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
			order.percentageGatherers = gathererAssign*0.01;
			order.percentageHunters = hunterAssign*0.01;
			
			World.getWorld().simulateWorld(order);
		}
		
		return super.onOptionsItemSelected(item);
	}
}
