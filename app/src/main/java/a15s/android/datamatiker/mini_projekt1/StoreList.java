package a15s.android.datamatiker.mini_projekt1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;


public class StoreList extends AppCompatActivity {
    public static final String EXTRA_LISTNO = "mini_projekt1_shopping_list_no";
    private Storage storage;
    private ArrayList<Store> stores = new ArrayList<Store>();
    private ArrayAdapter<Store> adapter;
    private long list_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);

        storage = new Storage(this);

        if(getIntent().hasExtra("listID")) {
            list_id = (int) getIntent().getExtras().get("listID");
        }

        updateList();

        ListView lView = (ListView) findViewById(R.id.storeListView);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, storage.getStores(), new String[] {"NAME"}, new int[]{android.R.id.text1});
        lView.setAdapter(adapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), TestWaresList.class);
                intent.putExtra("listID", list_id);
                intent.putExtra("storeID", id);
                startActivity(intent);
            }
        });

/*            adapter = new ArrayAdapter<Store>(this,
                    android.R.layout.simple_list_item_1,
                    stores);*/

/*            ListView listView = (ListView) findViewById(R.id.storeListView);
            listView.setAdapter(adapter);*/

           /* AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ShoppingListDetail.this, ShoppingItemDetail.class);
                    intent.putExtra(ShoppingItemDetail.EXTRA_ITEMID, adapter.getItem(position).getId());
                    startActivity(intent);
                }
            };
            listView.setOnItemClickListener(itemClickListener);*/

    }

    private void update(){


    }

    private void updateList() {
        Cursor cursor = null;
        SQLiteOpenHelper dbhelper = new Miniprojekt1DatabaseHelper(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        try {
            cursor = db.query("STORE",
                    new String[]{"_id", "NAME", "DESCRIPTION"},
                    null, null, null, null, null);
            ArrayList<Store> list = new ArrayList<>();

            while(cursor.moveToNext()) list.add(new Store( cursor.getInt(0), cursor.getString(1), cursor.getString(2)));

            stores = list;
        }
        catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        finally {
            if(cursor!=null) cursor.close();
            if(db!=null) db.close();
        }

    }
}
