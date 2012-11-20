/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nms.tasker.tasks;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Nathan M. Swan <nathanmswan@gmail.com>
 */
public class WeeklyTask extends Task {
     
    public WeeklyTask(String desc, int effort, long d, boolean[] days) {
        super(desc, effort, d);
        this.days = days;
        this.current = Calendar.getInstance();
        current.setTime(new Date(d));
        if (!days[current.get(Calendar.DAY_OF_WEEK)]) {
            // advance if not today
            advanceToNext();
        }
    }
    
    public void advanceToNext() {
        Calendar t = current;
        
        while (true) {
            t.add(Calendar.DAY_OF_WEEK, 1);
            int dow = t.get(Calendar.DAY_OF_WEEK);
            if (days[dow]) {
                mNixDueDate = t.getTimeInMillis();
                break;
            }
        }
    }
    
    public String dateString() {
        StringBuilder sb = new StringBuilder();
        sb.append("0 ");
        for(int i=0; i<days.length; i++) {
            sb.append(days[i] ? "1 " : "0 ");
        }
        sb.append(super.dateString());
        return sb.toString();
    }
    
    private boolean[] days;
    private Calendar current;
}
