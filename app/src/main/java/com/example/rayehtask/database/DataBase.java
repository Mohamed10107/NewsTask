package com.example.rayehtask.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.example.rayehtask.model.News;
import com.example.rayehtask.model.NewsDAO;

@Database(entities = { News.class }, version = 1)
public abstract class DataBase extends RoomDatabase
{
	private static final String DB_NAME = "database.db";
	private static volatile DataBase instance;

	// region singleton implementation
	public static synchronized DataBase getInstance(Context context)
	{
		if (instance == null)
		{
			instance = create(context);
		}
		return instance;
	}

	private static DataBase create(final Context context)
	{
		Builder<DataBase> builder = Room.databaseBuilder(context, DataBase.class, DB_NAME);
		return builder.build();
	}
	// endregion

	// region DAOs
	public abstract NewsDAO newsDAO();
	// endregion
}
