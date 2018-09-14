package com.example.rayehtask.utils;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import com.example.rayehtask.R;
import saschpe.android.customtabs.CustomTabsHelper;
import saschpe.android.customtabs.WebViewFallback;

public class ChromeManager
{
	public static void preLoadChromeService()
	{

	}

	public static void openLink(Context context, String URL)
	{
		CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().addDefaultShareMenuItem().setToolbarColor(context.getResources().getColor(R.color.colorPrimary)).setShowTitle(true).build();

		// This is optional but recommended
		CustomTabsHelper.addKeepAliveExtra(context, customTabsIntent.intent);

		// This is where the magic happens...
		CustomTabsHelper.openCustomTab(context, customTabsIntent, Uri.parse(URL), new WebViewFallback());
	}
}
