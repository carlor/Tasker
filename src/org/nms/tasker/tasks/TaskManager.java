/*
 * TaskManager.java - remembers task list
 * Copyright (C) 2012 Nathan M. Swan
 * 
 * This program is licensed under the GNU GPL.
 *
 */
package org.nms.tasker.tasks;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaskManager {
    // creates an empty task list, then opens from default.tasker file
    public TaskManager() throws FileNotFoundException, IOException {
        current = null;
        tf = new TaskFile();
        otherTasks = new ArrayList<Task>();
        tf.open(this);
    }
    
    // inserts task into the list, sorted by relative effort
    public void addTask(Task t) {
        if (current == null) {
            current = t;
        } else {
            for (int i=0; i<otherTasks.size(); i++) {
                if (otherTasks.get(i).getRelativeEffort() < t.getRelativeEffort()) {
                    otherTasks.add(i, t);
                    return;
                }
            }
            otherTasks.add(t);
        }
    }
    
    // the current task
    public Task currentTask() {
        assert(!isEmpty());
        return current;
    }
    
    // throws out the current task, replacing it with the next one
    public void nextTask() {
        if (otherTasks.isEmpty()) {
            current = null;
        } else {
            int result, nums=0;
            do {
                // there's a half chance it will pick one of the first two
                result = (int)Math.floor(Math.abs(rand.nextGaussian()*2.0));
                
                // if there's a bug, this will fail instead of loop forever
                assert(nums < 100);
                nums++;
                
            // on short lists, it might go over bounds
            } while (result >= otherTasks.size());
            
            current = otherTasks.remove(result);
        }
    }
    
    // make sure to check this before calling currentTask().
    public boolean isEmpty() {
        return current == null;
    }
    
    // iterates over all the tasks excluding the current one
    // non-deterministic order should be assumed
    public Iterable<Task> others() {
        return otherTasks;
    }
    
    // saves it to file
    public void save() throws FileNotFoundException, IOException {
        tf.save(this);
    }
    
    private Task current; // current task, null if empty
    private List<Task> otherTasks; // never null
    
    private TaskFile tf; // manages the .tasker file format

    private static Random rand = new Random(); // for choosing next task
}
