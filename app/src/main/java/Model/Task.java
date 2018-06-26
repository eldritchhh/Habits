package Model;

/**
 * Created by Francesco on 25/06/2018.
 */

public class Task {

    private String description;

    public Task(){ }

    public Task(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
