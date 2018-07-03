package com.example.android.habits.models;

import java.util.Date;
import java.util.List;

public class RemindMe {

    private String title;
    private List<Task> tasks;

    private Date scheduleTime;
    private List<Boolean> weekdays;

    private boolean active;

    public RemindMe() {
    }

    public RemindMe(String title, List<Task> tasks, Date scheduleTime, List<Boolean> weekdays) {
        this.title = title;
        this.tasks = tasks;
        this.scheduleTime = scheduleTime;
        this.setWeekdays(weekdays);
        this.active = true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Date getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Date scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public List<Boolean> getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(List<Boolean> weekdays) {
        this.weekdays = weekdays;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
