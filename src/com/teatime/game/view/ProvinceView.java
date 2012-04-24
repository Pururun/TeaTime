package com.teatime.game.view;

import android.content.res.Resources;
import android.graphics.Canvas;


import com.teatime.game.model.Province;

public class ProvinceView extends ImageXY{
	
	private Province province;
	
	private RiverView riverView = null;;
	
	public ProvinceView(Resources resources, int drawable, int x, int y, Province province) {
		super(resources, drawable, x, y);
		
		this.province = province;
		
		
		int riverViewX1 = x + 4;
		int riverViewY1 = y + 20;
		int riverViewX2 = x + 4;
		int riverViewY2 = y + 7;


//		this.riverView = RiverView.generateLinearRiverView(resources, riverViewX1, riverViewY1, riverViewX2, riverViewY2);
		this.riverView = RiverView.generateRealisticRiverView(resources, riverViewX1, riverViewY1, riverViewX2, riverViewY2);
	}
	
	public Province getProvince() {
		return province;
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		
		if(riverView != null) {
			riverView.draw(canvas);
		}
	}
}
