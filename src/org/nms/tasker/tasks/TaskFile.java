/*
 * TaskFile.java - reads/writes .tasker files
 * Copyright (C) 2012 Nathan M. Swan
 * 
 * This program is licensed under the GNU GPL.
 *
 */
package org.nms.tasker.tasks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class TaskFile {

    public TaskFile() {
        // the file is ~/default.tasker
        file = new File(System.getProperty("user.home").concat("/default.tasker"));
    }

    // given an empty TaskManager, puts tasks into it from file
    public void open(TaskManager tks) throws FileNotFoundException, IOException {
        file.createNewFile();
        Scanner sc = new Scanner(new FileInputStream(file));
        if (!sc.hasNext()) {
            // create empty
            save(tks);
        } else {
            // read in full
            if (!sc.next().equals("tasker0.1")) {
                throw new IOException("unsupported version");
            }
            String nxt = sc.next();
            if (!nxt.equals("empty")) {
                if (!nxt.equals("current:")) {
                    throw new IOException("invalid file");
                }
                tks.addTask(makeTask(sc));
                if (!sc.next().equals("others:")) {
                    throw new IOException("invalid file");
                }
                while (sc.hasNext()) {
                    tks.addTask(makeTask(sc));
                }
            }
        }
    }
    
    // puts the tasks into a file
    public void save(TaskManager tks) throws FileNotFoundException, IOException {
        file.createNewFile();
        PrintStream ps = new PrintStream(new FileOutputStream(file));
        ps.println("tasker0.1");
        if (tks.isEmpty()) {
            ps.println("empty");
        } else {
            ps.printf("current: %s\n", makeString(tks.currentTask()));
            ps.println("others:");
            for (Task t : tks.others()) {
                ps.println(makeString(t));
            }
        }
        ps.close();
    }
    
    // task -> string
    private String makeString(Task currentTask) {
        return String.format("%s %s %s",
                currentTask.getDescription().replace(' ', '_'),
                currentTask.getAbsoluteEffort(),
                currentTask.getMNixDueDate()
                );
    }
    
    // tokens -> task
    private Task makeTask(Scanner sc) {
        String desc = sc.next().replace('_', ' ');
        int eff = sc.nextInt();
        long mnixDue = sc.nextLong();
        return new Task(desc, eff, mnixDue);
    }
    
    // ~/default.tasker
    private File file;
}
