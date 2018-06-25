package Model;

/**
 * Created by Francesco on 25/06/2018.
 */

class Task {
    private String text;


    private Task(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
