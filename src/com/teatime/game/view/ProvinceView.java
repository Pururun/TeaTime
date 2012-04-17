package com.teatime.game.view;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.teatime.game.model.Province;

public class ProvinceView extends ImageXY{
	
	private Province province;
	
	public ProvinceView(Resources resources, int drawable, int x, int y, Province province) {
		super(resources, drawable, x, y);
		
		this.province = province;
	}
	

}
