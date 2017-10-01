package a15s.android.datamatiker.mini_projekt1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Sonnich on 01-10-2017.
 */

public class Storage {

    private SQLiteDatabase db;

    public Storage(Context context){
        db = new Miniprojekt1DatabaseHelper(context).getWritableDatabase();
    }

    public Cursor getStores(){

        return db.query("STORE", new String[] {"_id", "NAME", "DESCRIPTION"}, null, null, null, null, null);

    }

    public Cursor getWaresByStoreID(int id){
        Cursor cursor = db.query("WARE",
                                new String[] {"_id", "NAME", "UNIT_TYPE", "NORMAL_PRICE", "DISCOUNT_PRICE"},
                                "STORE_ID=?",
                                new String[] {Integer.toString(id)},
                                null, null, null);
        return cursor;
    }

    public boolean addItemToList(int listID, int amount, int itemID, int storeID){
        ContentValues item = new ContentValues();
        item.put("LIST_ID", listID);
        item.put("AMOUNT", amount);
        item.put("BOUGHT", false);
        item.put("WARE_ID", itemID);

        boolean succes = true;
        try {
            db.insert("SHOPPINGITEM", null, item);
        } catch (SQLException e){
            succes = false;
        }
        return succes;
    }


}
