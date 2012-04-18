// Done
package com.teatime.game.view;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.TypedValue;

public class ImageXY extends Image{
	
    private int x;
    private int y;
    
    public ImageXY(Resources res, int drawable, int x, int y) {
    	super(res, drawable);
    	
    	//this.x = x - mBitmap.getWidth() / 2;
        //this.y = y - mBitmap.getHeight() / 2;
    	
    	this.x = x;
    	this.y = y;
        
        //pixelsToDps(res);
    }
    
    public void draw(Canvas canvas) {
    	if( mBitmap != null) {
    		canvas.drawBitmap(mBitmap, x, y, null);
    	}
    }
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
    
    private void pixelsToDps(Resources res) {
    	x = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, x, res.getDisplayMetrics());
		y = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, y, res.getDisplayMetrics());
    }
}