
package com.teatime.game.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.View;

import com.teatime.game.R;
import com.teatime.game.model.Province;
import com.teatime.game.model.Tribe;
import com.teatime.game.model.World;
import com.teatime.game.util.GroupedTilesOrderedByAttribute;

public class MapView extends View{
	
	private List<ProvinceView> provinceViews;
	private List<RiverView> riverViews;
	private List<TribeView> tribeViews;
	
	private int provinceViewWidth;
	private int provinceViewHeight;
	
	private int size;
	

	public MapView(int mapOffsetX, int mapOffsetY, World world, Context context, Resources resources) {
		super(context);
		
		initializeMapView(mapOffsetX, mapOffsetY, world, resources);
	}

	private void initializeMapView(int mapOffsetX, int mapOffsetY, World world, Resources resources) {
		generateProvinceViews(mapOffsetX, mapOffsetY, world, resources);
		generateRiverViews(mapOffsetX, mapOffsetY, world, resources);
		generateTribeViews(0, 0, world, resources);
	}
	
	private void generateTribeViews(int offsetX, int offsetY, World world, Resources resources) {
		// Create list of provinceViews
		tribeViews = new LinkedList<TribeView>();
		
		List<Tribe> tribes = World.getWorld().getTribes();
		
		
		// Put tribeViews in their homeProvincesViews and create tribesViews
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

		provinceViewWidth = provinceImage.getBitmap().getWidth();
		provinceViewHeight = provinceImage.getBitmap().getHeight();

		// nr of provinces at one side
		size = (int)Math.sqrt(provinces.size());

		// Create provinceViews and position them as a checkboard
		for(Province province : World.getWorld().getProvinces()) {

			// Calculate the position of the new ProvinceView
			int posX = x * provinceViewWidth + mapOffsetX;
			int posY = y * provinceViewHeight + mapOffsetY;
			
			ProvinceView provinceView = new ProvinceView(resources, province.getIndex(), R.drawable.province, posX, posY, province);
			
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
	
	private void generateRiverViews(int mapOffsetX, int mapOffsetY, World world, Resources resources) {
		
		final int terrainAttributeWater = 2;
		GroupedTilesOrderedByAttribute gr = new GroupedTilesOrderedByAttribute(provinceViews, terrainAttributeWater);
		List<List<ProvinceView>> groupsOfProvinceViews = gr.evaluate();
		
		riverViews = new LinkedList<RiverView>();
		
		for(List<ProvinceView> groupOfProvinceViews : groupsOfProvinceViews) {
			
			if(groupOfProvinceViews != null) {
				int indexX1;
				int indexY1;
				
				Random random = new Random();
				
				if(groupOfProvinceViews.size() > 1) {
					
					indexX1 = groupOfProvinceViews.get(0).getProvince().getIndex() % size;
					indexY1 = groupOfProvinceViews.get(0).getProvince().getIndex() / size;
					
					int indexX2 = groupOfProvinceViews.get(1).getProvince().getIndex() % size;
					int indexY2 = groupOfProvinceViews.get(1).getProvince().getIndex() / size;
					
					int randomX = random.nextInt(provinceViewWidth);
					int randomY = random.nextInt(provinceViewHeight);
					
					int x1 = provinceViewWidth * indexX1 + mapOffsetX + randomX;
					int y1 = provinceViewHeight * indexY1 + mapOffsetY + randomY;
					
					randomX = random.nextInt(provinceViewWidth);
					randomY = random.nextInt(provinceViewHeight);
					
					int x2 = provinceViewWidth * indexX2 + mapOffsetX + randomX;
					int y2 = provinceViewHeight * indexY2 + mapOffsetY + randomY;
				
					riverViews.add(RiverView.generateRealisticRiverView(resources, x1, y1, x2, y2));
					
					for(int i = 1; i < groupOfProvinceViews.size(); i++) {
						indexX1 = indexX2;
						indexY1 = indexY2;
						
						x1 = x2;
						y1 = y2;
						
						indexX2 = groupOfProvinceViews.get(i).getProvince().getIndex() % size;
						indexY2 = groupOfProvinceViews.get(i).getProvince().getIndex() / size;
						
						randomX = random.nextInt(provinceViewWidth);
						randomY = random.nextInt(provinceViewHeight);
						
						x2 = provinceViewWidth * indexX2 + mapOffsetX + randomX;
						y2 = provinceViewHeight * indexY2 + mapOffsetY + randomY;
						
						riverViews.add(RiverView.generateRealisticRiverView(resources, x1, y1, x2, y2));
					}
				} else {
					
					indexX1 = groupOfProvinceViews.get(0).getProvince().getIndex() % size;
					indexY1 = groupOfProvinceViews.get(0).getProvince().getIndex() / size;
					
					int randomX = random.nextInt(provinceViewWidth);
					int randomY = random.nextInt(provinceViewHeight);
					
					int x1 = provinceViewWidth * indexX1 + mapOffsetX + randomX;
					int y1 = provinceViewHeight * indexY1 + mapOffsetY + randomY;
					
					randomX = random.nextInt(provinceViewWidth);
					randomY = random.nextInt(provinceViewHeight);
					
					int x2 = provinceViewWidth * indexX1 + mapOffsetX + randomX;
					int y2 = provinceViewHeight * indexY1 + mapOffsetY + randomY;
					
					riverViews.add(RiverView.generateRealisticRiverView(resources, x1, y1, x2, y2));
				}
			}
		}		
	}
	
	private void findAdjacentProvinceViews(ProvinceView provinceView) {
		List<ProvinceView> neighbours = new LinkedList<ProvinceView>();
		neighbours.add(provinceView);
		
		List<ProvinceView> returnProvinceViews = new LinkedList<ProvinceView>();
		
		while(!neighbours.isEmpty()) {
			
		}
	}
	
	
	private ProvinceView findProvince(List<ProvinceView> provinces, int index) {
		return null;
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
		
		// Draw all rivers
		for(RiverView riverView : riverViews) {
			riverView.draw(canvas);
		}
		
		// Draw all tribes
		for(TribeView tribeView : tribeViews) {
			tribeView.draw(canvas);
		}
	}
}