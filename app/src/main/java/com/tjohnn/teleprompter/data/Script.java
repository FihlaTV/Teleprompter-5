package com.tjohnn.teleprompter.data;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "scripts")
public class Script {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;

    private String text;

    private Date telepromptingDate;

    private boolean isTeleprompted;

    private Date createdAt;

    public Script(long id, String title, String text, Date telepromptingDate, boolean isTeleprompted, Date createdAt) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.telepromptingDate = telepromptingDate;
        this.isTeleprompted = isTeleprompted;
        this.createdAt = createdAt;
    }

    @Ignore
    public Script(String title, String text, Date telepromptingDate, boolean isTeleprompted, Date createdAt) {
        this.title = title;
        this.text = text;
        this.telepromptingDate = telepromptingDate;
        this.isTeleprompted = isTeleprompted;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTelepromptingDate() {
        return telepromptingDate;
    }

    public void setTelepromptingDate(Date telepromptingDate) {
        this.telepromptingDate = telepromptingDate;
    }

    public boolean isTeleprompted() {
        return isTeleprompted;
    }

    public void setTeleprompted(boolean teleprompted) {
        isTeleprompted = teleprompted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
