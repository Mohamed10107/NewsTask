package com.example.rayehtask.repository;

import android.content.Context;
import com.example.rayehtask.database.DataBase;
import com.example.rayehtask.model.News;
import com.example.rayehtask.webserivces.NewsResponse;
import com.example.rayehtask.webserivces.NewsWebservices;
import java.util.ArrayList;
import java.util.Date;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsRepository
{
	private NewsWebservices webservices;
	private ArrayList<News> news = new ArrayList<>();

	// region singleton implementation
	private static class Loader
	{
		static NewsRepository INSTANCE = new NewsRepository();
	}

	public static NewsRepository getInstance()
	{
		return Loader.INSTANCE;
	}

	private NewsRepository()
	{
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

		String BASE_URL = "https://newsapi.org/v2/";
		Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
		webservices = retrofit.create(NewsWebservices.class);
	}
	// endregion

	public Observable<ArrayList<News>> getNews(Context context)
	{
		return Observable.create(emitter -> {
			if (news != null && !news.isEmpty())
			{
				emitter.onNext(news);
				emitter.onComplete();
			}
			else
			{
				news = new ArrayList<>(DataBase.getInstance(context).newsDAO().getNews());
				if (!news.isEmpty())
				{
					emitter.onNext(news);
					emitter.onComplete();
				}
				else
				{
					getNewsFromAPI(context, emitter);
				}
			}
		});
	}

	private void getNewsFromAPI(Context context, ObservableEmitter<ArrayList<News>> emitter)
	{
		webservices.getNews().subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(new Observer<NewsResponse>()
		{
			@Override
			public void onSubscribe(Disposable d)
			{

			}

			@Override
			public void onNext(NewsResponse response)
			{
				news = response.articles;
				for (int i = 0; i < news.size(); i++)
				{
					News newsItem = news.get(i);
					newsItem.id = String.valueOf(++i);
					newsItem.date = newsItem.date.replace("T", "  ").replace("Z", "");
				}
				DataBase.getInstance(context).newsDAO().insert(news);
				emitter.onNext(news);
				emitter.onComplete();
			}

			@Override
			public void onError(Throwable e)
			{
				emitter.onError(e);
				emitter.onComplete();
			}

			@Override
			public void onComplete()
			{

			}
		});
	}

	public Observable<ArrayList<News>> getFavoriteNews(Context context)
	{
		return Observable.create(emitter -> {
			if (news != null && !news.isEmpty())
			{
				emitter.onNext(findFavoriteNews());
				emitter.onComplete();
			}
			else
			{
				news = new ArrayList<>(DataBase.getInstance(context).newsDAO().getNews());
				if (!news.isEmpty())
				{
					emitter.onNext(findFavoriteNews());
					emitter.onComplete();
				}
				else
				{
					emitter.onNext(new ArrayList<>());
					emitter.onComplete();
				}
			}
		});
	}

	private ArrayList<News> findFavoriteNews()
	{
		ArrayList<News> favoriteNews = new ArrayList<>();
		for (int i = 0; i < news.size(); i++)
		{
			if (news.get(i).favorite == News.IS_FAVORITE)
			{
				favoriteNews.add(news.get(i));
			}
		}
		return favoriteNews;
	}

	public Observable<ArrayList<News>> updateFavoriteNews(Context context, String newsId, int favoriteState, boolean showFavoriteOnly)
	{
		return Observable.create(emitter -> {
			for (int i = 0; i < news.size(); i++)
			{
				if (news.get(i).id.equals(newsId))
				{
					news.get(i).favorite = favoriteState;
					DataBase.getInstance(context).newsDAO().update(news.get(i));
					break;
				}
			}
			if (showFavoriteOnly)
			{
				emitter.onNext(findFavoriteNews());
			}
			else
			{
				emitter.onNext(news);
			}
			emitter.onComplete();
		});
	}
}
