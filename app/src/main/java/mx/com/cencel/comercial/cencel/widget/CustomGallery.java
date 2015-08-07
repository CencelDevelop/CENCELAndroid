package mx.com.cencel.comercial.cencel.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * Galeria personalizada para mostrar un elemento a la vez en cada fling
 * 
 * @author Vanesa Cid Garcia
 */
public class CustomGallery extends Gallery {
	
	private CustomGalleryEventListener customGalleryEventListener;

	public CustomGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomGallery(Context context) {
		super(context);
	}

	public void setCustomGalleryEventListener(
			CustomGalleryEventListener customGalleryEventListener) {
		this.customGalleryEventListener = customGalleryEventListener;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		super.onFling(e1, e2, velocityX / 4, velocityY);
		if(customGalleryEventListener != null) {
			customGalleryEventListener.onFling(e1, e2, velocityX, velocityY);
		}
		return false;
	}
	
	public static interface CustomGalleryEventListener {
		
		public void onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);
		
	}

}
