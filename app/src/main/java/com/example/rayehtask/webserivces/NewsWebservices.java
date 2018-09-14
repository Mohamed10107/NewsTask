package com.example.rayehtask.webserivces;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface NewsWebservices
{
	@GET("everything?q=google&apiKey=c63cb6b1ed8347afbed03d4f249f56ff")
	Observable<NewsResponse> getNews();
}
