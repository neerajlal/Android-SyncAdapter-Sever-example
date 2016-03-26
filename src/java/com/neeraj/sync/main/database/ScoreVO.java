/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neeraj.sync.main.database;

/**
 *
 * @author n33raj
 */
public class ScoreVO {

    int _id;
    String name;
    String score;
    int dirty;

    public ScoreVO() {
    }

    public ScoreVO(int _id, String name, String score, int dirty) {
        this._id = _id;
        this.name = name;
        this.score = score;
        this.dirty = dirty;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getDirty() {
        return dirty;
    }

    public void setDirty(int dirty) {
        this.dirty = dirty;
    }
}
