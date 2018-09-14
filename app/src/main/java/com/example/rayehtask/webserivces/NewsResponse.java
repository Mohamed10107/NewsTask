package com.example.rayehtask.webserivces;

import com.example.rayehtask.model.News;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class NewsResponse
{
	@SerializedName("articles")
	public ArrayList<News> articles = new ArrayList<>();
}
