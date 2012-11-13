/*
 * CurrentTaskPane.java - displays the current task
 * Copyright (C) 2012 Nathan M. Swan
 * 
 * This program is licensed under the GNU GPL.
 * 
 */
package org.nms.tasker.ui;

import org.nms.tasker.tasks.Task;

public class CurrentTaskPane extends javax.swing.JPanel {

    // Creates new form CurrentTaskPane
    public CurrentTaskPane() {
        initComponents();
    }
    
    // updates labels to this current task
    public void show(Task task) {
        if (task == null) {
            jLabel1.setText("<html><b>Congrats! You're done!</b></html>");
            jLabel2.setText("");
            jLabel3.setText("");
            jLabel4.setText("");
        } else {
            jLabel1.setText(String.format("<html><b>%s</b></html>", task.getDescription()));
            jLabel2.setText(String.format("Absolute Effort: %d", task.getAbsoluteEffort()));
            jLabel3.setText(String.format("Days Left: %d", task.daysLeft()));
            jLabel4.setText(String.format("Relative Effort: %d", task.getRelativeEffort()));
        }
    }

    // NetBeans-generated form code
    // WARNING: Do NOT modify; it can be changed by the Form Editor
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jLabel1.setText("<html><b>Do Whatever</b></html>");

        jLabel2.setText("Absolute Effort: 50");

        jLabel3.setText("Days Left: 2");

        jLabel4.setText("Relative Effort: 25");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                    .add(jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                    .add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                    .add(jLabel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel3)
                .add(18, 18, 18)
                .add(jLabel4)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
