package Model;

import java.util.List;

/**
 * Created by Francesco on 25/06/2018.
 */

public class OnClickRemindMe extends RemindMe {
    private int schedultime;

    public OnClickRemindMe(){
        super();
        this.schedultime = 0;
    }

    public OnClickRemindMe(String title, List<Task> elements, int schedultime) {
        super(title, elements);
        this.schedultime = schedultime;
    }

    public int getSchedultime() {
        return schedultime;
    }

    public void setSchedultime(int schedultime) {
        this.schedultime = schedultime;
    }
}
