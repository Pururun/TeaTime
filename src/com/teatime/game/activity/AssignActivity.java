package com.teatime.game.activity;

import android.os.Bundle;
import android.widget.TextView;

/**
 * 
 * Staplar
 * 
 * @author 
 *
 */
public class AssignActivity extends BaseActivity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the AssignActivity tab");
        setContentView(textview);
    }
}
