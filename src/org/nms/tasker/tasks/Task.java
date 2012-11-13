/*
 * Task.java - contains information on a task
 * Copyright (C) 2012 Nathan M. Swan
 * 
 * This program is licensed under the GNU GPL.
 *
 */
package org.nms.tasker.tasks;

import java.util.Date;

public class Task {
    // creates a task with the given parameters
    public Task(String description, int effort, long due) {
        this.description = description;
        this.absEffort = effort;
        this.mNixDueDate = due;
    }
    
    // the days between the due date and today, rounded up
    public int daysLeft() {
        long now = (new Date()).getTime();
        long dist = mNixDueDate - now;
        return (int)Math.ceil(dist / (1000.0*60*60*24));
    }
    
    private String description;
    private int absEffort;
    private long mNixDueDate;

    // one-liner human-readable description of the task
    public String getDescription() {
        return description;
    }
    
    // estimated energy required, on a 0-100 scale
    public int getAbsoluteEffort() {
        return absEffort;
    }
    
    // due date, in milliseconds since Unix epoch
    public long getMNixDueDate() {
        return mNixDueDate;
    }
    
    // absolute effort / days left
    public int getRelativeEffort() {
        return (int)Math.ceil(getAbsoluteEffort()*1.0 / Math.abs(daysLeft()));
    }
} 
