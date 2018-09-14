package com.example.rayehtask.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface NewsDAO
{
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(ArrayList<News> news);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(News news);

	@Update
	void update(News news);

	@Query("SELECT * FROM news;")
	List<News> getNews();
}
