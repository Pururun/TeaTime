package com.teatime.game.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.content.res.Resources;
import android.graphics.Canvas;

import com.teatime.game.R;
import com.teatime.game.util.Vector2D;

public class RiverView {
	
	private List<ImageXY> waterTiles;
	
	private static int lastX;
	private static int lastY;
	
	private static double nextX;
	private static double nextY;
	
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

	public static RiverView generateRealisticRiverView(Resources resources, int x1, int y1, int x2, int y2) {

		lastX = Integer.MIN_VALUE;
		lastY = Integer.MIN_VALUE;
		nextX = new Integer(Integer.MAX_VALUE);
		nextY = new Integer(Integer.MAX_VALUE);
		
		Vector2D vector = new Vector2D(x1, y1, x2, y2);

		double x = x1;
		double y = y1;

		double destinationX = x2;
		double destinationY = y2;

		List<ImageXY> waterTiles = new LinkedList<ImageXY>();
		double maxValue = new Integer(Integer.MAX_VALUE);
		
		do {
			
			if(nextX != maxValue && nextY != maxValue) {
				x = nextX;
				y = nextY;
				
				vector = new Vector2D((int)x, (int)y, x2, y2);
			}
			
			plot(resources, x, y, x + vector.getX(), y + vector.getY(), waterTiles);
			x += vector.getX();
			y += vector.getY();
			
		} while((Math.abs(destinationX - x) + Math.abs(destinationY - y)) >= 8 );
				
		return new RiverView(waterTiles);
	}

	private static void plot(Resources resources, double currentX, double currentY, double optimalX, double optimalY, List<ImageXY> waterTiles) {
		
		// Draw dice
		Random random = new Random();
		
		int dice = random.nextInt(16);
		
		double dX = (optimalX - currentX) * 0.5;
		double dY = (optimalY - currentY) * 0.5;
		
		double middleX = currentX + dX;
		double middleY = currentY + dY;
		
		double vX = currentX - middleX;
		double vY = currentY - middleY;
		
		dX = vY;
		dY = -vX;
		
		if(dice < 3) {
			nextX = middleX + dX;
			nextY = middleY + dY;
		}
		else if(dice < 6) {
			nextX = middleX - dX;
			nextY = middleY - dY;
		}
		else if (dice < 11){
			nextX = optimalX;
			nextY = optimalY;
		}
		else if(dice < 13) {
			double lenX = optimalX - currentX;
			double lenY = optimalY - currentY;
			
			vX = lenY;
			vY = -lenX;
			
			nextX = currentX + vX;
			nextY = currentY + vY;
		}
		else {
			double lenX = optimalX - currentX;
			double lenY = optimalY - currentY;
			
			vX = lenY;
			vY = -lenX;
			
			nextX = currentX - vX;
			nextY = currentY - vY;
		}
		
		// Do not plot the same spot twice
		if(nextX == lastX && nextY == lastY)
			return;
		
		ImageXY waterTile = new ImageXY(resources, R.drawable.water_tile, (int)nextX, (int)nextY);
		waterTiles.add(waterTile);
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
