package com.example.oguerisck.wearnotifications.Model;

/**
 * Created by Oguerisck on 21/09/2018.
 */


import java.util.Date;

public class EventModel {
    public String uid;
    public String title;
    public String body;
    public String address;
    public Date begin;
    public Date end;

    public EventModel() {
    }

    public EventModel(String title, String body, String address, Date begin, Date end) {
        this.title = title;
        this.body = body;
        this.address = address;
        this.begin = begin;
        this.end = end;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}