package com.example.rayehtask.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Dimensions
{
	public static int dp_to_px(int dp)
	{
		return Math.round(dp * (Resources.getSystem().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
	}

	public static int px_to_dp(int px)
	{
		return Math.round(px / (Resources.getSystem().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
	}
}
