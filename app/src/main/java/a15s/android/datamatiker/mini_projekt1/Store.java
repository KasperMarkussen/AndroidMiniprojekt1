package a15s.android.datamatiker.mini_projekt1;

/**
 * Created by Marcus on 28-03-2017.
 */

public class Store {
    private int _id;
    private String name;
    private String description;

    public Store (int id, String name, String description) {
        this._id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() { return _id; }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public String toString() {
        return name;
    }
}
