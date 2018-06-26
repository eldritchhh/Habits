package Model;

import java.util.List;

/**
 * Created by Francesco on 25/06/2018.
 */

public class ScheduledRemindMe extends OnClickRemindMe {

    private int scheduleTime;
    private boolean active;

    public ScheduledRemindMe(){ }

    public ScheduledRemindMe(String title, List<Task> tasks, int scheduleTime) {
        super(title, tasks, true);
        this.scheduleTime = scheduleTime;
        this.active = true;
    }

    public int getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(int scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
