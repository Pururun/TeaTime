package com.teatime.game.util;

public class Vector2D {
	
	private double x;
	private double y;

	public Vector2D(int x1, int y1, int x2, int y2) {
		
		x = x2 - x1;
		y = y2 - y1;
		
		normalize();
	}
	
	private void normalize() {
		
		double length = Math.sqrt( x*x + y*y );
		
		x = x / length;
		y = y / length;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
