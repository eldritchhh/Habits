package Model;

import java.util.List;

/**
 * Created by Francesco on 25/06/2018.
 */

public class RemindMe {
    private String title;
    private List<Task> elements;

    public RemindMe(){
        this.title = "";
        this.elements = null;
    }

    public RemindMe(String title, List<Task> elements){
        this.title = title;
        this.elements = elements;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Task> getElements() {
        return elements;
    }

    public void setElements(List<Task> elements) {
        this.elements = elements;
    }
}
