package com.example.android.habits.models;

import java.util.List;

public class OnClickRemindMe {
    private String title;
    private List<Task> tasks;
    private boolean estScheduled;

    public OnClickRemindMe() {
    }

    public OnClickRemindMe(String title, List<Task> tasks, boolean estScheduled) {
        this.title = title;
        this.tasks = tasks;
        this.estScheduled = estScheduled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OnClickRemindMe that = (OnClickRemindMe) o;
        return this.title.equals(that.title);
    }

    // GETTER SETTER

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

    public boolean isEstScheduled() {
        return estScheduled;
    }

    public void setEstScheduled(boolean isScheduled) {
        this.estScheduled = isScheduled;
    }
}
