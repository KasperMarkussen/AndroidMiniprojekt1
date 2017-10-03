package a15s.android.datamatiker.mini_projekt1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by Marcus on 28-03-2017.
 */

public class ShoppingItem {
    private String wareName;
    private String unit;
    private int amount;
    private int listId;
    private double normalPrice = 0;
    private double discountPrice = 0;
    private Date discountEndDate = new Date();
    private String storeName;
    private int id;
    private boolean bought = false;

    public ShoppingItem(int id,
                        String wareName,
                        String unit,
                        int amount,
                        int listId,
                        double normalPrice,
                        double discountPrice,
                        Date discountEndDate,
                        String storeName,
                        boolean bought) {
        this.id = id;
        this.wareName = wareName;
        this.unit = unit;
        this.amount = amount;
        this.listId = listId;
        this.normalPrice = normalPrice;
        this.discountPrice = discountPrice;
        this.discountEndDate = discountEndDate;
        this.storeName = storeName;
        this.bought = bought;
    }
    public ShoppingItem(int id, Ware ware, int amount, int listId, String storeName) {
        this(
                id,
                ware.getName(),
                ware.getUnit(),
                amount,listId,
                ware.getNormalPrice(),
                ware.getDiscountPrice(),
                ware.getDiscountEndDate(),
                storeName,
                false
        );
    }

    public String getWareName() {
        return wareName;
    }
    public int getAmount() {
        return amount;
    }
    public int getListId() {
        return listId;
    }
    public int getId() {
        return id;
    }
    public String getUnit() {
        return unit;
    }
    public String getStoreName() {
        return storeName;
    }
    public double getDiscountPrice() {
        return discountPrice;
    }
    public Date getDiscountEndDate() {
        return discountEndDate;
    }
    public double getNormalPrice() {
        return normalPrice;
    }
    public double getPrice() {
        if(isDiscountValid()) return amount * discountPrice;
        else return amount * normalPrice;
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

    public boolean isBought() {
        return bought;
    }
    public void setBought(boolean b) {
        bought = b;
    }

    public String toString(){
        return wareName;
    }

    public void save(Context context){
        SQLiteOpenHelper dbhelper = new Miniprojekt1DatabaseHelper(context);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        try {
            ContentValues item = new ContentValues();
            item.put("WARE_NAME", wareName);
            item.put("UNIT", unit);
            item.put("NORMAL_PRICE", normalPrice);
            item.put("DISCOUNT_PRICE", discountPrice);
            item.put("DISCOUNT_END_DATE", discountEndDate.getTime());
            item.put("LIST_ID", listId);
            item.put("AMOUNT", amount);
            item.put("STORE_NAME", storeName);
            item.put("BOUGHT", bought);
            db.update("SHOPPINGITEM", item, "_id = ?", new String [] {Integer.toString(this.id)});
        }
        catch (SQLiteException e) {
            Toast toast = Toast.makeText(context, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        finally {
            if(db!=null) db.close();
        }
    }
}
