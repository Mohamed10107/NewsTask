package com.example.rayehtask.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "news")
public class News
{
	@ColumnInfo(name = "id")
	@PrimaryKey
	@NonNull
	public String id = "";

	@ColumnInfo(name = "author")
	@SerializedName("author")
	public String author;

	@ColumnInfo(name = "title")
	@SerializedName("title")
	public String title;

	@ColumnInfo(name = "description")
	@SerializedName("description")
	public String description;

	@ColumnInfo(name = "date")
	@SerializedName("publishedAt")
	public String date;

	@ColumnInfo(name = "url")
	@SerializedName("url")
	public String url;

	@ColumnInfo(name = "urlToImage")
	@SerializedName("urlToImage")
	public String urlToImage;

	@ColumnInfo(name = "content")
	@SerializedName("content")
	public String content;

	@ColumnInfo(name = "favorite")
	public int favorite = NOT_FAVORITE;

	@Ignore
	public static final int NOT_FAVORITE = 0;

	@Ignore
	public static final int IS_FAVORITE = 1;

}
