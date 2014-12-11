/*
 * FileMenu.java - manages the file opening menu 
 * Copyright (C) 2013 Nathan M. Swan
 * 
 * This program is licensed under the GNU GPL.
 * 
 */
package org.nms.tasker.ui;

import java.awt.FileDialog;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import org.nms.tasker.tasks.TFList;
import org.nms.tasker.tasks.TaskManager;

public class FileMenu {
    public static void createMenuBar(MainFrame mf) throws IOException {
        FileMenu fm = new FileMenu(mf);
        MenuBar r = new MenuBar();
        r.add(fm.menu);
        mf.setMenuBar(r);
    }
    
    private FileMenu(MainFrame mf) throws IOException {
        menu = new Menu();
        menu.setLabel("Choose List...");
        tfList = new TFList();
        frame = mf;
        frame.openTaskManager(tfList.openAtIndex(0));
        render();
    }
    
    private void render() {
        menu.removeAll();
        int len = tfList.size();
        for (int i=0; i < len; i++) {
            MenuItem mi = new MenuItem();
            TaskManager tm = tfList.getAtIndex(i);
            mi.setLabel(tm.getFileName().replace(".tasker", ""));
            final int j=i;
            mi.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent aevt) {
                    frame.openTaskManager(tfList.openAtIndex(j));
                    render();
                }
            });
            menu.add(mi);
        }
        menu.addSeparator();
        
        addFileMenu("Create New List...", FileDialog.SAVE);
        addFileMenu("Open New List...", FileDialog.LOAD);
    }
    
    private void addFileMenu(String label, final int mode) {
        MenuItem mi = new MenuItem();
        mi.setLabel(label);
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent aevt) {
                FileDialog fd = new FileDialog(frame);
                fd.setMode(mode);
                fd.setFilenameFilter(new FilenameFilter() {
                    @Override
                    public boolean accept(File file, String string) {
                        return string.toLowerCase().endsWith(".tasker");
                    }
                });
                
                fd.setVisible(true);
                
                String fname = fd.getFile();
                if (fname != null) {
                    if (!fname.toLowerCase().endsWith(".tasker")) {
                        fname = fname.concat(".tasker");
                    }
                    File f = new File(fname);
                    try {
                        frame.openTaskManager(tfList.openFile(f));
                        render();
                    } catch (IOException ioe) {
                        frame.handle(ioe);
                    }
                }
            }
        });
        menu.add(mi);
    }
    
    private Menu menu;
    private TFList tfList;
    private MainFrame frame;
}
