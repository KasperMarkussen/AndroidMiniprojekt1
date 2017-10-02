package a15s.android.datamatiker.mini_projekt1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Sonnich on 01-10-2017.
 */

public class Storage {

    private static SQLiteDatabase db;

    public Storage(Context context){

    }

    public static Cursor getStores(Context context){

        return readQuery(context,
                "STORE",
                new String[] {"_id", "NAME", "DESCRIPTION"},
                null, null, null, null, null);

    }

    public static Cursor getWaresByStoreID(Context context, int id){
        return readQuery(context,
                "WARE",
                new String[] {"_id", "NAME", "UNIT_TYPE", "NORMAL_PRICE", "DISCOUNT_PRICE"},
                "STORE_ID=?",
                new String[] {Integer.toString(id)},
                null, null, null);
    }

    public static Cursor getShoppingLists(Context context){
        return readQuery(context,
                "SHOPPINGLIST",
                new String[] {"_id", "NAME"},
                null,null,null,null,null);
    }

    public static boolean addNewList(Context context, String name) {

        ContentValues list = new ContentValues();

        list.put("NAME", name);

        return insertQuery(context, "SHOPPINGLIST", null, list);
    }

    public static boolean addItemToList(Context context, int listID, int amount, int wareID, int storeID){

        ContentValues item = new ContentValues();
        item.put("LIST_ID", listID);
        item.put("AMOUNT", amount);
        item.put("BOUGHT", false);

        Cursor ware = readQuery(context,
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

        Cursor store = readQuery(context,
                "STORE",
                new String[]{"NAME"},
                "_id=?",
                new String[] {Integer.toString(storeID)},
                null,null,null);
        store.moveToFirst();

        item.put("STORE_NAME", store.getString(0));
        item.put("BOUGHT", false);

        return insertQuery(context, "SHOPPINGITEM", null, item);
    }

    public static boolean removeList(Context context, int listId) {
        boolean success = false;
        if(removeQuery(context, "SHOPPINGLIST", "_id=?", new String[]{Integer.toString(listId)}))
            success = removeQuery(context, "SHOPPINGITEM", "LIST_ID=?", new String[]{Integer.toString(listId)});
        return success;
    }

    private static boolean insertQuery(Context context, String table, String nullColumnHack, ContentValues values) {

        SQLiteOpenHelper dbhelper = new Miniprojekt1DatabaseHelper(context);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        boolean success = false;
        try {
            db.insert(table, nullColumnHack, values);
            success = true;
        }
        catch (SQLiteException e) {
            Toast toast = Toast.makeText(context, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        /*finally {
            if(db!=null) db.close();
        }*/
        return success;
    }

    private static Cursor readQuery(Context context, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {


        Cursor cursor = null;
        SQLiteOpenHelper dbhelper = new Miniprojekt1DatabaseHelper(context);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try {
            cursor = db.query(
                    table,
                    columns,
                    selection,
                    selectionArgs,
                    groupBy,
                    having,
                    orderBy);
        }
        catch (SQLiteException e) {
            Toast toast = Toast.makeText(context, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        /*finally {
            if(db!=null) db.close();
        }*/
        return cursor;

    }

    private static boolean removeQuery(Context context, String table, String whereClause, String[] whereArgs) {

        SQLiteOpenHelper dbhelper = new Miniprojekt1DatabaseHelper(context);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        boolean success = false;
        try {
            db.delete(table, whereClause, whereArgs);
            success = true;
        }
        catch (SQLiteException e) {
            Toast toast = Toast.makeText(context, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        /*finally {
            if(db!=null) db.close();
        }*/
        return success;
    }

}
