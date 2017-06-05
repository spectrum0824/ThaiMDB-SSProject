package com.sourcey.materiallogindemo;

/**
 * Created by spectrum on 6/5/2017 AD.
 */

public class Review {
    private String title;
    private String name;
    private String url;
    private long score;
    private String des;
    private String id;
    private long relia;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDes() {

        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public long getScore() {

        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getRelia() {
        return relia;
    }

    public void setRelia(long relia) {
        this.relia = relia;
    }

    public Review(String title, String name, String url, long score, String des, String id, long relia) {
        this.title = title;
        this.name = name;
        this.url = url;
        this.score = score;
        this.relia = relia;
        this.des = des;
        this.id = id;

    }
}
