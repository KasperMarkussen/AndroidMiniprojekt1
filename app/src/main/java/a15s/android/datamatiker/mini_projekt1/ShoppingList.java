package a15s.android.datamatiker.mini_projekt1;

/**
 * Created by Marcus on 27-03-2017.
 */

public class ShoppingList {
    private String name;
    private int _id;

    public ShoppingList(String name) {
        this.name = name;
    }
    public ShoppingList(String name, int id) {this(name); this._id = id;}

    public String getName() {
        return name;
    }
    public int getId() {
        return _id;
    }


    public String toString() {
        return name;
    }
}
