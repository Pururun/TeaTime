package com.teatime.game.view;

import java.util.LinkedList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Canvas;

import com.teatime.game.R;
import com.teatime.game.util.Vector2D;

public class RiverView {
	private List<ImageXY> waterTiles;
	
	private static int lastX = Integer.MIN_VALUE;
	private static int lastY = Integer.MIN_VALUE;
	
	private RiverView(List<ImageXY> waterTiles) {
		this.waterTiles = waterTiles;
	}
	
	public static RiverView generateLinearRiverView(Resources resources, int x1, int y1, int x2, int y2) {
		
		Vector2D vector = new Vector2D(x1, y1, x2, y2);
		
		double x = x1;
		double y = y1;
		
		double destinationX = x2;
		double destinationY = y2;
		
		List<ImageXY> waterTiles = new LinkedList<ImageXY>();
		
		do {
			plot(resources, x, y, waterTiles);
			x += vector.getX();
			y += vector.getY();
			
		} while((Math.abs(destinationX - x) >= 1) && (Math.abs(destinationY - y) >= 1));
		
		return new RiverView(waterTiles);
	}
	
	private static void plot(Resources resources, double x, double y, List<ImageXY> waterTiles) {
		int plotX = (int)x;
		int plotY = (int)y;
		
		// Do not plot the same spot twice
		if(plotX == lastX && plotY == lastY)
			return;
		
		ImageXY waterTile = new ImageXY(resources, R.drawable.water_tile, plotX, plotY);
		waterTiles.add(waterTile);
	}
	
	public void draw(Canvas canvas) {
		for(ImageXY riverTile : waterTiles) {
			riverTile.draw(canvas);
		}
	}
}
