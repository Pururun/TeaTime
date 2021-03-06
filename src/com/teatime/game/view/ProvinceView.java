package com.teatime.game.view;

import android.content.res.Resources;
import android.graphics.Canvas;


import com.teatime.game.model.Province;

//TODO: implement index for easier lookup

public class ProvinceView extends ImageXY{
	
	private Province province;
	
//	// Will be lifted out of
//	private RiverView riverView = null;
	
	public ProvinceView(Resources resources, int index, int drawable, int x, int y, Province province) {
		super(resources, drawable, x, y);
		
		
		this.province = province;
		
		
		int riverViewX1 = x + 20;
		int riverViewY1 = y + 4;
		int riverViewX2 = x + 20;
		int riverViewY2 = y + 20;


//		this.riverView = RiverView.generateLinearRiverView(resources, riverViewX1, riverViewY1, riverViewX2, riverViewY2);
		
//		if(province.getHasWater() == true) {
//			this.riverView = RiverView.generateRealisticRiverView(resources, riverViewX1, riverViewY1, riverViewX2, riverViewY2);
//		}
	}
	
	public Province getProvince() {
		return province;
	}	
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		
//		if(riverView != null) {
//			riverView.draw(canvas);
//		}
	}
}
