package me.ashif.newsapp.model;

/**
 * Created by almukthar on 27/12/15.
 */
import java.util.ArrayList;

public class news {
    private String title, thumbnailUrl;
    String url;


    public news() {
    }

    public news(String name, String thumbnailUrl, String url) {
        this.title = name;
        this.thumbnailUrl = thumbnailUrl;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getUrl() {
        return url;
    }

    public void seturl(String url) {
        this.url = url;
    }

}







