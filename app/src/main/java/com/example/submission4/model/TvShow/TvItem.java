package com.example.submission4.model.TvShow;

import android.os.Parcel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TvItem{

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<ResultsTv> results;

	@SerializedName("total_results")
	private int totalResults;

	public TvItem(Parcel source) {

	}

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setResults(List<ResultsTv> results){
		this.results = results;
	}

	public List<ResultsTv> getResults(){
		return results;
	}

	public void setTotalResults(int totalResults){
		this.totalResults = totalResults;
	}

	public int getTotalResults(){
		return totalResults;
	}


	@Override
 	public String toString(){
		return 
			"TvItem{" + 
			"page = '" + page + '\'' + 
			",total_pages = '" + totalPages + '\'' + 
			",results = '" + results + '\'' + 
			",total_results = '" + totalResults + '\'' + 
			"}";
		}
}