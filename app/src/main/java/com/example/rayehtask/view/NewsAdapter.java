package com.example.rayehtask.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.rayehtask.R;
import com.example.rayehtask.model.News;
import com.example.rayehtask.utils.ChromeManager;
import com.example.rayehtask.utils.Views;
import com.example.rayehtask.utils.listeners.FavoriteNewsChangeListener;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	private Context context;
	private FavoriteNewsChangeListener listener;
	private ArrayList<News> news = new ArrayList<>();

	NewsAdapter(Context context, FavoriteNewsChangeListener listener)
	{
		this.context = context;
		this.listener = listener;
	}

	public void setData(ArrayList<News> news)
	{
		this.news = new ArrayList<>();
		this.news.addAll(news);
		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_news_item, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
	{
		ViewHolder viewHolder = (ViewHolder) holder;
		News newsItem = getItem(position);

		viewHolder.title.setText(newsItem.title);
		viewHolder.description.setText(newsItem.description);
		viewHolder.date.setText(newsItem.date);
		viewHolder.pic.setImageDrawable(Views.TextDrawableView.textDrawable(60, 60, "G"));
		if (newsItem.urlToImage != null && !newsItem.urlToImage.isEmpty())
		{
			Views.ImageLoader.load(context, viewHolder.pic, newsItem.urlToImage, Views.TextDrawableView.textDrawable(60, 60, "G"));
		}

		if (newsItem.favorite == News.NOT_FAVORITE)
		{
			viewHolder.favorite.setImageResource(R.mipmap.non_favorite_icon);
		}
		else
		{
			viewHolder.favorite.setImageResource(R.mipmap.favorite_icon);
		}
	}

	@Override
	public int getItemCount()
	{
		return news.size();
	}

	private News getItem(int position)
	{
		return news.get(position);
	}

	public class ViewHolder extends RecyclerView.ViewHolder
	{
		RoundedImageView pic;
		TextView title, date, description;
		ImageView favorite;

		ViewHolder(View itemView)
		{
			super(itemView);
			itemView.setOnClickListener(view -> {
				News selectedItem = getItem(getLayoutPosition());
				if (selectedItem.url != null && !selectedItem.url.isEmpty())
				{
					ChromeManager.openLink(context, getItem(getLayoutPosition()).url);
				}
			});
			pic = itemView.findViewById(R.id.pic);
			title = itemView.findViewById(R.id.title);
			date = itemView.findViewById(R.id.date);
			description = itemView.findViewById(R.id.description);
			favorite = itemView.findViewById(R.id.favorite);
			favorite.setOnClickListener(view -> {
				News selectedItem = getItem(getLayoutPosition());
				if (selectedItem.favorite == News.NOT_FAVORITE)
				{
					selectedItem.favorite = News.IS_FAVORITE;
				}
				else
				{
					selectedItem.favorite = News.NOT_FAVORITE;
				}
				notifyItemChanged(getLayoutPosition());
				listener.onItemChanged(selectedItem.id, selectedItem.favorite);
			});
		}
	}
}