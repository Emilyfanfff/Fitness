package com.example.fitnessapp.model;

public class Statistics {
    private String likeCount;

    private String viewCount;

    private String favoriteCount;

    private String commentCount;

    public String getLikeCount ()
    {
        return likeCount;
    }

    public void setLikeCount (String likeCount)
    {
        this.likeCount = likeCount;
    }

    public String getViewCount ()
    {
        return viewCount;
    }

    public void setViewCount (String viewCount)
    {
        this.viewCount = viewCount;
    }

    public String getFavoriteCount ()
    {
        return favoriteCount;
    }

    public void setFavoriteCount (String favoriteCount)
    {
        this.favoriteCount = favoriteCount;
    }

    public String getCommentCount ()
    {
        return commentCount;
    }

    public void setCommentCount (String commentCount)
    {
        this.commentCount = commentCount;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [likeCount = "+likeCount+", viewCount = "+viewCount+", favoriteCount = "+favoriteCount+", commentCount = "+commentCount+"]";
    }
}
