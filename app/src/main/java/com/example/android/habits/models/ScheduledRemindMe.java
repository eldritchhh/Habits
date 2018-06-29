package com.example.android.habits.models;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class ScheduledRemindMe extends OnClickRemindMe {

    private Date scheduleTime;
    private List<Boolean> weekdays;
    private boolean active;

    public ScheduledRemindMe() {
    }

    public ScheduledRemindMe(String title, List<Task> tasks, Date scheduleTime, List<Boolean> weekdays) {
        super(title, tasks, true);
        this.scheduleTime = scheduleTime;
        this.active = true;
        this.setWeekdays(weekdays);
    }

    public Date getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Date scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Boolean> getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(List<Boolean> weekdays) {
        this.weekdays = weekdays;
    }
}
