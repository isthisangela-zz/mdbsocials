package com.example.isthisangela.mdbsocials;

import java.util.Date;

/**
 * Created by isthisangela on 2/22/18.
 */

public class Event {
    Date date;
    String title, description, email, pic;
    int interested;

    public Event(Date date, String title, String description, String email, String pic) {
        this.date = date;
        this.title = title;
        this.description = description;
        this.email = email;
        this.pic = pic;
        this.interested = 0;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public String getPic() {
        return pic;
    }

    public int getInterested() {
        return interested;
    }

    public void setInterested(int interested) {
        this.interested = interested;
    }
}
