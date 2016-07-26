package com.example.satriadimaspermana.movieapp;

/**
 * Created by Satria Dimas Permana on 7/14/2016.
 */
public class Const {
    public static final String URL_API_RATING = "http://api.themoviedb.org/3/discover/movie?api_key=1b2f29d43bf2e4f3142530bc6929d341&sort_by=vote_average.desc";
    public static final String URL_API_POPULAR = "http://api.themoviedb.org/3/discover/movie?api_key=1b2f29d43bf2e4f3142530bc6929d341&sort_by=popularity.desc";
    public static final String URL_API_UPCOMMING = "http://api.themoviedb.org/3/discover/movie?api_key=1b2f29d43bf2e4f3142530bc6929d341&sort_by=upcomming.desc";
    public static final String URL_API_REVIEWS = "http://api.themoviedb.org/3/movie/{id}/reviews?api_key=1b2f29d43bf2e4f3142530bc6929d341";
}
