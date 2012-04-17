package com.teatime.game.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.teatime.game.R;

public class HomeActivity extends BaseActivity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        
        setupNewGameButton();
    }
    
    private void setupNewGameButton() {
    	Button newGameButton = (Button) findViewById(R.id.new_game);
        newGameButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, GameMainActivity.class);
				startActivity(intent);
			}
		});
    }
}