/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;

/**
 *
 */
public class Project {
    private int id;
    private String title;
    private User owner;
    private Date goalTime;
    private Date createdAt;
    private Date updateAt;

    public Project(int id, String title, User owner, Date goalTime, Date createdAt, Date updateAt) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.goalTime = goalTime;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

    public Project() {
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Date getGoalTime() {
        return goalTime;
    }

    public void setGoalTime(Date goalTime) {
        this.goalTime = goalTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    
}
