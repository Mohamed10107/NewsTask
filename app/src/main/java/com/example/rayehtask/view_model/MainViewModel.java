package com.example.rayehtask.view_model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import com.example.rayehtask.R;
import com.example.rayehtask.model.News;
import com.example.rayehtask.repository.NewsRepository;
import java.util.ArrayList;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel
{
	private boolean showFavoriteOnly = false;

	private MutableLiveData<ArrayList<News>> news = new MutableLiveData<>();

	public MutableLiveData<ArrayList<News>> news()
	{
		return news;
	}

	private MutableLiveData<String> error = new MutableLiveData<>();

	public MutableLiveData<String> error()
	{
		return error;
	}

	public void getNews(Context context)
	{
		showFavoriteOnly = false;
		NewsRepository.getInstance().getNews(context).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(new Observer<ArrayList<News>>()
		{
			@Override
			public void onSubscribe(Disposable d)
			{

			}

			@Override
			public void onNext(ArrayList<News> googleNews)
			{
				if (googleNews != null)
				{
					news.postValue(googleNews);
				}
				else
				{
					news.postValue(new ArrayList<>());
				}
			}

			@Override
			public void onError(Throwable e)
			{
				news.postValue(new ArrayList<>());
				error.postValue(context.getString(R.string.news_error));
			}

			@Override
			public void onComplete()
			{

			}
		});
	}

	public void getFavoriteNews(Context context)
	{
		showFavoriteOnly = true;
		NewsRepository.getInstance().getFavoriteNews(context).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(new Observer<ArrayList<News>>()
		{
			@Override
			public void onSubscribe(Disposable d)
			{

			}

			@Override
			public void onNext(ArrayList<News> googleNews)
			{
				if (googleNews != null)
				{
					news.postValue(googleNews);
				}
				else
				{
					news.postValue(new ArrayList<>());
				}
			}

			@Override
			public void onError(Throwable e)
			{
				news.postValue(new ArrayList<>());
				error.postValue(context.getString(R.string.news_error));
			}

			@Override
			public void onComplete()
			{

			}
		});
	}

	public void updateFavoriteNews(Context context, String id, int favoriteState)
	{
		NewsRepository.getInstance().updateFavoriteNews(context, id, favoriteState, showFavoriteOnly).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(new Observer<ArrayList<News>>()
		{
			@Override
			public void onSubscribe(Disposable d)
			{

			}

			@Override
			public void onNext(ArrayList<News> googleNews)
			{
				if (googleNews != null)
				{
					news.postValue(googleNews);
				}
				else
				{
					news.postValue(new ArrayList<>());
				}
			}

			@Override
			public void onError(Throwable e)
			{
				news.postValue(new ArrayList<>());
				error.postValue(context.getString(R.string.news_error));
			}

			@Override
			public void onComplete()
			{

			}
		});
	}
}
