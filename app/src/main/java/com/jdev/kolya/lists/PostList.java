package com.jdev.kolya.lists;

import java.util.Date;

/**
 * Created by Java_Dude on 8/29/2018.
 */

public class PostList extends postid{
    String desc,id,localtime,title,url,type;
    Date time;

    public PostList()
    {

    }
    public PostList(String desc, String id, String localtime, String title, String url, String type, Date time) {

        this.desc = desc;
        this.id = id;
        this.localtime = localtime;
        this.title = title;
        this.url = url;
        this.type = type;
        this.time = time;
    }
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocaltime() {
        return localtime;
    }

    public void setLocaltime(String localtime) {
        this.localtime = localtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


}
