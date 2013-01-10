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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class TaskFile {
    public static String LOCAL_NAME = "default.tasker";

    public TaskFile() {
        // the file is ~/default.tasker
        file = new File(
                System.getProperty("user.home")
                        .concat(System.getProperty("file.separator"))
                        .concat(LOCAL_NAME));
    }
    
    public TaskFile(boolean noDefault) {
        file = null;
    }
    
    // given an empty TaskManager, puts tasks into it from file
    public void open(TaskManager tks) throws FileNotFoundException, IOException {
        file.createNewFile();
        openFrom(tks, new FileInputStream(file));
    }
    
    public void openFrom(TaskManager tks, InputStream is)
            throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(is);
        if (!sc.hasNext()) {
            // create empty
            save(tks);
        } else {
            // read in full
            String token = sc.next();
            if (!(token.equals("tasker0.1") || token.equals("tasker0.2"))) {
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
        saveTo(tks, new FileOutputStream(file));
    }
    
    public void saveTo(TaskManager tks, OutputStream os) 
            throws FileNotFoundException, IOException {
        PrintStream ps = new PrintStream(os);
        ps.println("tasker0.2");
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
                currentTask.dateString());
    }
    
    // tokens -> task
    private Task makeTask(Scanner sc) {
        String desc = sc.next().replace('_', ' ');
        int eff = sc.nextInt();
        long mnixDue = sc.nextLong();
        if (mnixDue == 0) {
            boolean[] dates = new boolean[8];
            for(int i=0; i<dates.length; i++) {
                dates[i] = sc.nextInt() != 0;
            }
            long mnixCurDue = sc.nextLong();
            return new WeeklyTask(desc, eff, mnixCurDue, dates);
        } else {
            return new Task(desc, eff, mnixDue);
        }
    }
    
    // ~/default.tasker
    private File file;
}
