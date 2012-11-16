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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class TaskManager implements Comparator<Task> {
    // creates an empty task list, then opens from default.tasker file
    public TaskManager() throws FileNotFoundException, IOException {
        current = null;
        tf = new TaskFile();
        otherTasks = new ArrayList<Task>();
        tf.open(this);
    }
    
    // the current task
    public Task currentTask() {
        assert(!isEmpty());
        return current;
    }
    
    // inserts task into the list, sorted by relative effort
    public void addTask(Task t) {
        if (current == null) {
            current = t;
        } else {
            otherTasks.add(t);
            resort();
        }
    }
   
    // gets the next task, forgetting about this one for a second
    public void putOffCurrentTask() {
        notEmpty();
        Task temp = currentTask();
        nextTask();
        addTask(temp);
    }
    
    // throw out current task
    public void completeCurrentTask() {
        notEmpty();
        nextTask();
    }
    
    // finds a new task, considering this one
    // to be used after addign things,
    // deciding to see if something is more important
    public void shuffle() {
        notEmpty();
        addTask(currentTask());
        nextTask();
    }
    
    // throws out the current task, replacing it with the next one
    private void nextTask() {
        if (otherTasks.isEmpty()) {
            current = null;
        } else {
            int result, nums=0;
            resort();
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
    
    // makes sure that there's no null access
    private void notEmpty() {
        if (isEmpty()) {
            throw new java.lang.IllegalStateException("empty task list");
        }
    }
    
    // sorts tasks by relative effort
    private void resort() {
        Collections.sort(otherTasks, this);
    }
    
    // compare relative effort
    public int compare(Task a, Task b) {
        return b.getRelativeEffort() - a.getRelativeEffort();
    }
    
    // saves it to file
    public void save() throws FileNotFoundException, IOException {
        tf.save(this);
    }
    
    // make sure to check this before calling currentTask().
    public boolean isEmpty() {
        return current == null;
    }
    
    // iterates over all the tasks excluding the current one
    // non-deterministic order should be assumed
    public Iterable<Task> others() {
        resort();
        return otherTasks;
    }
    
    private Task current; // current task, null if empty
    private List<Task> otherTasks; // never null
    
    private TaskFile tf; // manages the .tasker file format

    private static Random rand = new Random(); // for choosing next task
}
