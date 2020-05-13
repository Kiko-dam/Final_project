package sample.data;

public class Unit
{
    int number;
    String title;

    public Unit(int number, String title)
    {
        this.number = number;
        this.title = title;
    }

    public int getNumber() {
        return number;
    }


    @Override
    public String toString() {
        return title;
    }
}
