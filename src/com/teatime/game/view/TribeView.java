package com.teatime.game.view;

import android.content.res.Resources;

import com.teatime.game.model.Tribe;

public class TribeView extends ImageXY{
	
	//private Tribe tribe;
	
	//private ProvinceView provinceView;
	
	
	//TODO: move provinceView x and y and make offsets
	public TribeView(Resources resources, int mapX, int mapY, int drawable, ProvinceView provinceView, Tribe tribe) {
		super(resources, drawable, provinceView.getX() + mapX, provinceView.getY() + mapY);
		
	//	this.tribe = tribe;
	//	this.provinceView = provinceView;
	}
}
