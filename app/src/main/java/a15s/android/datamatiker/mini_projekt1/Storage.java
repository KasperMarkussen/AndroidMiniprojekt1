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

    public boolean addItemToList(int listID, int amount, int wareID, int storeID){

        ContentValues item = new ContentValues();
        item.put("LIST_ID", listID);
        item.put("AMOUNT", amount);
        item.put("BOUGHT", false);

        Cursor ware = db.query(
                "WARE",
                new String[]{"NAME", "UNIT_TYPE", "NORMAL_PRICE", "DISCOUNT_PRICE", "DISCOUNT_END_DATE" },
                "_id=?",
                new String[] {Integer.toString(wareID)},
                null,null,null);
        ware.moveToFirst();

        item.put("WARE_NAME", ware.getString(0));
        item.put("UNIT", ware.getString(1));
        item.put("NORMAL_PRICE", ware.getDouble(2));
        item.put("DISCOUNT_PRICE", ware.getDouble(3));
        item.put("DISCOUNT_END_DATE", ware.getInt(4));

        Cursor store = db.query(
                "STORE",
                new String[]{"NAME"},
                "_id=?",
                new String[] {Integer.toString(storeID)},
                null,null,null);
        store.moveToFirst();

        item.put("STORE_NAME", store.getString(0));
        item.put("BOUGHT", false);

        boolean succes = true;
        try {
            db.insert("SHOPPINGITEM", null, item);
        } catch (SQLException e){
            succes = false;
        }
        return succes;
    }


}
