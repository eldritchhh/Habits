package ListObserver;

import java.util.List;
import java.util.Observable;

import Model.OnClickRemindMe;
import Model.ScheduledRemindMe;

import static Singleton.Singleton.ON_CLICK_LIST;
import static Singleton.Singleton.SCHEDULED_LIST;

public class ListsObservable extends Observable {

    public static ListsObservable observable;

    private boolean scheduledListReady;
    private boolean onClickListReady;

    private List<OnClickRemindMe> onClickRemindMeList;
    private List<ScheduledRemindMe> scheduledRemindMeList;

    public ListsObservable() {
        scheduledListReady = false;
        onClickListReady = false;
    }

    public synchronized static ListsObservable getInstance() {
        if (observable == null)
            observable = new ListsObservable();
        return observable;
    }

    public void setValues(String listName, Object list) {
        switch (listName) {
            case SCHEDULED_LIST:
                scheduledListReady = true;
                scheduledRemindMeList = (List<ScheduledRemindMe>) list;
                break;
            case ON_CLICK_LIST:
                onClickListReady = true;
                onClickRemindMeList = (List<OnClickRemindMe>) list;
                break;
            default:
        }

        if (scheduledListReady && onClickListReady){
            setChanged();
            notifyObservers(onClickRemindMeList);
        }
    }
}
