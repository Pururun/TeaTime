package com.teatime.game.view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Image {
	 protected Bitmap mBitmap = null;
	    
	    public Image(Resources res, int drawable) {
	        mBitmap = BitmapFactory.decodeResource(res, drawable);
	    }
	    
	    public Image() {
	    }
	 
	    public Bitmap getBitmap() {
	    	return mBitmap;
	    }
}
