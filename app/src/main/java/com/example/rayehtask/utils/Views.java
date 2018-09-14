package com.example.rayehtask.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.example.rayehtask.view.LoadingDialog;
import com.squareup.picasso.Picasso;

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
		public static void load(Context context, ImageView imageView, String imagePath, Drawable placeHolder)
		{
			Picasso.with(context).load(imagePath).placeholder(placeHolder).into(imageView);
		}
	}
}
