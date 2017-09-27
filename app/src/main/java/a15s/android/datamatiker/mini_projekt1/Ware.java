package a15s.android.datamatiker.mini_projekt1;

import java.util.Date;

/**
 * Created by Marcus on 28-03-2017.
 */

public class Ware {
    private int _id;
    private String name;
    private String unit = "n/a";
    private double normalPrice = 0;
    private double discountPrice = 0;
    private Date discountEndDate = new Date();
    private int storeId;

    public Ware(int id, String name)
    {
        this._id = id;
        this.name = name;
    }
    public Ware(int id,
                String name,
                String unit,
                double normalPrice,
                int store)
    {
        this(id, name);
        this.unit = unit;
        this.normalPrice = normalPrice;
        this.storeId = store;
    }
    public Ware(int id,
                String name,
                String unit,
                double normalPrice,
                int store,
                double discountPrice,
                Date discountEndDate)
    {
        this(id, name,unit,normalPrice,store);
        this.discountPrice = discountPrice;
        this.discountEndDate = discountEndDate;
    }

    public String getName(){
        return this.name;
    }
    public String getUnit() {
        return this.unit;
    }
    public double getNormalPrice() {
        return this.normalPrice;
    }
    public double getDiscountPrice() {
        return this.discountPrice;
    }
    public Date getDiscountEndDate() {
        return this.discountEndDate;
    }
    public int getStoreId() {
        return storeId;
    }
    public int getId () {
        return _id;
    }

    public double getPrice() {
        if(isDiscountValid()) return discountPrice;
        else return normalPrice;
    }
    public double getDiscountSize() {
        if(isDiscountValid()) return normalPrice - discountPrice;
        else return 0;
    }
    public boolean isDiscountValid() {
        Date currentTime = new Date();
        if(currentTime.before(this.discountEndDate))
            return true;
        else return false;
    }

    public String toString() {
        return this.name;
    }
}
