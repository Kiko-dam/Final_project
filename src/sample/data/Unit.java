package sample.data;

import java.util.List;

public class Unit
{
    int number;
    String title;

    public Unit(int number, String title)
    {
        this.number = number;
        this.title = title;
    }
    //List<Section> sections;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /* public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    } */

    @Override
    public String toString() {
        return title;
    }
}
