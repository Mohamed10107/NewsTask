package com.example.rayehtask.view;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.rayehtask.R;
import com.example.rayehtask.model.News;
import com.example.rayehtask.utils.Views;
import com.example.rayehtask.utils.listeners.FavoriteNewsChangeListener;
import com.example.rayehtask.view_model.MainViewModel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FavoriteNewsChangeListener, View.OnClickListener
{
	MainViewModel viewModel;
	Button all, favorites;
	RecyclerView news;
	TextView emptyView;
	NewsAdapter adapter;
	Views.LoadingView loading;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loading = new Views.LoadingView(this);
		viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
		initListeners();
		initView();
		viewModel.getNews(this);
		loading.show();
	}

	private void initView()
	{
		all = findViewById(R.id.all);
		favorites = findViewById(R.id.favorites);

		all.setOnClickListener(this);
		favorites.setOnClickListener(this);

		emptyView = findViewById(R.id.empty_view);
		news = findViewById(R.id.news);
		news.setHasFixedSize(true);
		news.setLayoutManager(new LinearLayoutManager(this));
		adapter = new NewsAdapter(this, this);
		news.setAdapter(adapter);
		setNewsDataAndView(new ArrayList<>());
	}

	private void setNewsDataAndView(ArrayList<News> newsList)
	{
		adapter.setData(newsList);
		if (newsList.size() > 0)
		{
			news.setVisibility(View.VISIBLE);
			emptyView.setVisibility(View.GONE);
		}
		else
		{
			news.setVisibility(View.GONE);
			emptyView.setVisibility(View.VISIBLE);
		}
	}

	private void initListeners()
	{
		viewModel.news().observe(this, newsList -> {
			loading.dismiss();
			if (newsList != null)
			{
				setNewsDataAndView(newsList);
			}
			else
			{
				setNewsDataAndView(new ArrayList<>());
			}
		});

		viewModel.error().observe(this, error -> Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show());
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.all:
			viewModel.getNews(this);
			break;
		case R.id.favorites:
			viewModel.getFavoriteNews(this);
			break;
		}
	}

	@Override
	public void onItemChanged(String id, int newFavoriteState)
	{
		viewModel.updateFavoriteNews(this, id, newFavoriteState);
	}
}
