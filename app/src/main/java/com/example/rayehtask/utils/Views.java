package com.example.rayehtask.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.example.rayehtask.view.LoadingDialog;

public class Views
{
	public static class LoadingView
	{
		private LoadingDialog loading;

		public LoadingView(Context context)
		{
			loading = new LoadingDialog(context);
		}

		public void show()
		{
			if (loading != null && !loading.isShowing())
			{
				loading.show();
			}
		}

		public void dismiss()
		{
			if (loading != null && loading.isShowing())
			{
				loading.dismiss();
			}
		}
	}

	public static class TextDrawableView
	{
		public static TextDrawable textDrawable(int width, int height, String text)
		{
			return TextDrawable.builder().beginConfig().height(Dimensions.dp_to_px(height)).width(Dimensions.dp_to_px(width)).textColor(Color.WHITE).toUpperCase().endConfig().buildRoundRect(String.valueOf(text.charAt(0)), 0xFF3ABA9A, Dimensions.dp_to_px(7));
		}
	}

	public static class ImageLoader
	{
		public static void load(Context context, String imageURl, String auth, ImageView imageView, Drawable placeHolder)
		{
			Glide.with(context).load(new GlideUrl(imageURl, new LazyHeaders.Builder().addHeader("Authorization", auth).build())).thumbnail(0.1f).apply(new RequestOptions().placeholder(placeHolder)).into(imageView);
		}

		public static void load(Context context, String imagePath, ImageView imageView, Drawable placeHolder)
		{
			Glide.with(context).load(imagePath).thumbnail(0.1f).apply(new RequestOptions().placeholder(placeHolder)).into(imageView);
		}
	}
}
