package a15s.android.datamatiker.mini_projekt1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Marcus on 28-03-2017.
 */

public class Miniprojekt1DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "miniprojekt1";
    private static final int DB_VERSION = 1;

    Miniprojekt1DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        TestData testData = new TestData();

        db.execSQL(
                "CREATE TABLE STORE ("
                        +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        +"NAME TEXT, "
                        +"DESCRIPTION TEXT);"
        );
        for(Store element : testData.TEST_STORES) {
            ContentValues storeValues = new ContentValues();
            storeValues.put("NAME", element.getName());
            storeValues.put("DESCRIPTION", element.getDescription());
            db.insert("STORE", null, storeValues);
        }

        db.execSQL(
                "CREATE TABLE WARE ("
                        +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        +"NAME TEXT, "
                        +"UNIT_TYPE TEXT, "
                        +"NORMAL_PRICE REAL, "
                        +"DISCOUNT_PRICE REAL, "
                        +"DISCOUNT_END_DATE INTEGER, "
                        +"STORE_ID INTEGER);"
        );
        for(Ware element : testData.TEST_WARES) {
            ContentValues wareValues = new ContentValues();
            wareValues.put("NAME", element.getName());
            wareValues.put("UNIT_TYPE", element.getUnit());
            wareValues.put("NORMAL_PRICE", element.getNormalPrice());
            wareValues.put("DISCOUNT_PRICE", element.getDiscountPrice());
            wareValues.put("DISCOUNT_END_DATE", element.getDiscountEndDate().getTime());
            wareValues.put("STORE_ID", element.getStoreId());
            db.insert("WARE", null, wareValues);
        }
        // Git Test Hello!
        db.execSQL(
                "CREATE TABLE SHOPPINGLIST ("
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"NAME TEXT);"
        );
        for(ShoppingList element : testData.TEST_LISTS) {
            ContentValues listValues = new ContentValues();
            listValues.put("NAME", element.getName());
            db.insert("SHOPPINGLIST", null, listValues);
        }

        db.execSQL(
                "CREATE TABLE SHOPPINGITEM ("
                        +"_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        +"WARE_NAME TEXT, "
                        +"UNIT TEXT, "
                        +"AMOUNT INTEGER, "
                        +"LIST_ID INTEGER, "
                        +"NORMAL_PRICE REAL, "
                        +"DISCOUNT_PRICE REAL, "
                        +"DISCOUNT_END_DATE INTEGER, "
                        +"STORE_NAME TEXT, "
                        +"BOUGHT NUMERIC);"
        );
        for(ShoppingItem elem : testData.TEST_ITEMS) {
            ContentValues items = new ContentValues();
            items.put("WARE_NAME", elem.getWareName());
            items.put("UNIT", elem.getUnit());
            items.put("AMOUNT", elem.getAmount());
            items.put("LIST_ID", elem.getListId());
            items.put("NORMAL_PRICE", elem.getNormalPrice());
            items.put("DISCOUNT_PRICE", elem.getDiscountPrice());
            items.put("DISCOUNT_END_DATE", elem.getDiscountEndDate().getTime());
            items.put("STORE_NAME", elem.getStoreName());
            items.put("BOUGHT", elem.isBought());
            db.insert("SHOPPINGITEM", null, items);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
