package com.teatime.game.view;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.View;

import com.teatime.game.R;
import com.teatime.game.model.Province;
import com.teatime.game.model.Tribe;
import com.teatime.game.model.World;

public class MapView extends View{
	
	private List<ProvinceView> provinceViews;
	private List<TribeView> tribeViews;
	

	public MapView(int mapOffsetX, int mapOffsetY, World world, Context context, Resources resources) {
		super(context);
		
		initializeMapView(mapOffsetX, mapOffsetY, world, resources);
	}

	private void initializeMapView(int mapOffsetX, int mapOffsetY, World world, Resources resources) {
		generateProvinceViews(mapOffsetX, mapOffsetY, world, resources);
		generateTribeViews(0, 0, world, resources);
	}
	
	private void generateTribeViews(int offsetX, int offsetY, World world, Resources resources) {
		// Create list of provinceViews
		tribeViews = new LinkedList<TribeView>();
		
		List<Tribe> tribes = World.getWorld().getTribes();
		
		for(Tribe tribe : tribes) {
			
			for(ProvinceView provinceView : provinceViews) {
				
				Province province = provinceView.getProvince();
				if(tribe.getHomeProvince() == province) {
					TribeView tribeView = new TribeView(resources, offsetX, offsetY, R.drawable.tribe, provinceView, tribe);
					tribeViews.add(tribeView);
				}
			}
		}
	}

	private void generateProvinceViews(int mapOffsetX, int mapOffsetY, World world, Resources resources) {
		// Create list of provinceViews
		provinceViews = new LinkedList<ProvinceView>();

		List<Province> provinces = World.getWorld().getProvinces();

		// pixels right
		int x = 0;

		// pixels down
		int y = 0;

		// Create a temporary image of a province to retrieve width and height
		Image provinceImage = new Image(resources, R.drawable.province);

		// Retrieve width and height values

		//TODO: Fix wrong size bug by adding the missing offset or change getWidth and 
		// 		getHeight to something else
		int provinceViewWidth = provinceImage.getBitmap().getWidth();
		int provinceViewHeight = provinceImage.getBitmap().getHeight();

		// nr of provinces at one side
		int size = (int)Math.sqrt(provinces.size());

		// Create provinceViews and position them as a checkboard
		for(Province province : World.getWorld().getProvinces()) {

			// Calculate the position of the new ProvinceView
			int posX = x * provinceViewWidth + mapOffsetX;
			int posY = y * provinceViewHeight + mapOffsetY;
			
			ProvinceView provinceView = new ProvinceView(resources, R.drawable.province, posX, posY, province);
			
			provinceViews.add(provinceView);
			
			// increment horizontal
			x++;
			
			// increment vertical
			if(x == size) {
				y++;
				x = 0;
			}
		}
	}

	public void draw() {
		invalidate();
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		
		// Draw all provinces
		for(ProvinceView provinceView : provinceViews) {
			provinceView.draw(canvas);
		}
		
		// Draw all tribes
		for(TribeView tribeView : tribeViews) {
			tribeView.draw(canvas);
		}
	}

}
