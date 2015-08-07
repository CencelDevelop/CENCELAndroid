package mx.com.cencel.comercial.cencel.activities.promos;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Gallery.LayoutParams;

import mx.com.cencel.comercial.cencel.pojo.PromoSlideFotos;
import mx.com.cencel.comercial.cencel.util.CencelUtils;


/**
 * Adapter personalizado para una galeria de banners
 * 
 * @author Vanesa Cid Garcia
 */
public class BannerGalleryAdapter extends RemoteImageGalleryAdapter {

	public BannerGalleryAdapter(Activity context, List<PromoSlideFotos> bannerElements, Drawable defaultDrawable) {
		super(context, null, null, null, defaultDrawable);
		List<String> imageUrls = new ArrayList<String>();
		for(PromoSlideFotos bannerElement : bannerElements) {
			imageUrls.add(CencelUtils.buildUrlPhoto(context, bannerElement.getFotoActual()));
		}
		setImageUrls(imageUrls);
	}

	@Override
	protected void onGetView(int position, ImageView remoteImageView, ViewGroup remoteImageViewContainer, ViewGroup parent) {
		super.onGetView(position, remoteImageView, remoteImageViewContainer, parent);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.CENTER;
		remoteImageView.setAdjustViewBounds(true);
		remoteImageView.setLayoutParams(lp);
		
		remoteImageViewContainer.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	
}
