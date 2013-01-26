/*
 * TFList.java - manages the a list of TaskManagers
 * Copyright (C) 2013 Nathan M. Swan
 * 
 * This program is licensed under the GNU GPL.
 * 
 */
package org.nms.tasker.tasks;

// list-like api

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TFList {
    public TFList() throws IOException {
        list = new ArrayList<TaskManager>();
        openFile(TaskFile.DEFAULT_FILE);
    }
    
    public TaskManager openFile(File f) throws IOException {
        TaskManager tm = new TaskManager(f);
        list.add(0, tm);
        return tm;
    }
    
    public TaskManager openAtIndex(int i) {
        TaskManager r = list.remove(i);
        list.add(0, r);
        return r;
    }
    
    public int size() { return list.size(); }
    public TaskManager getAtIndex(int i) { return list.get(i); }
    
    private ArrayList<TaskManager> list; 
}
