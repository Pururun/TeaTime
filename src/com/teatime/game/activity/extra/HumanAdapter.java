package com.teatime.game.activity.extra;

import java.util.ArrayList;
import java.util.List;

import com.teatime.game.R;
import com.teatime.game.model.Human;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HumanAdapter extends ArrayAdapter<Human> {
	
	List<Human> humans = new ArrayList<Human>();
	int textViewResourceId;
	Context context;

	public HumanAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.textViewResourceId = textViewResourceId;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(textViewResourceId, parent, false);
		}
		Human h = humans.get(position);
		if (h != null) {
			TextView nameText = (TextView) convertView.findViewById(R.id.name);
			TextView ageText = (TextView) convertView.findViewById(R.id.age);
			TextView sexText = (TextView) convertView.findViewById(R.id.sex);
			TextView craftText = (TextView) convertView
					.findViewById(R.id.currentCraft);
			TextView expText = (TextView) convertView
					.findViewById(R.id.currentCraftExp);
			if (nameText != null) {
				nameText.setText(h.getName().toUpperCase());
			}
			if (ageText != null) {
				ageText.setText("age: " + h.getAge());
			}
			if (sexText != null) {
				sexText.setText("sex: " + h.getSex());
			}
			if (craftText != null ) {
				String craft = h.isAdult() && h.getCurrentCraft() != null ? h.getCurrentCraft().toString() : "None";
				craftText.setText(craft);
			}
			if (expText != null && h.isAdult() && h.getCurrentCraft() != null) {
				expText.setText("" + Util.round(h.getCurrentCraft().getExperience(), 2));
			} else {
				expText.setText("");
			}
		}
		
		return convertView;
	}
	
	@Override
	public void add(Human object) {
		super.add(object);
		humans.add(object);
	}
}
