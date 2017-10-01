package a15s.android.datamatiker.mini_projekt1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WareInStore extends AppCompatActivity {

    private ArrayList<Ware> wareList = new ArrayList<Ware>();
    private ArrayAdapter<Ware> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateList();

        adapter = new ArrayAdapter<ShoppingItem>(this,
                android.R.layout.simple_list_item_1,
                shoppingList);
        ListView listView = (ListView) findViewById(R.id.shoppingListView);
        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WareInStore.this, WareInStore.class);
                intent.putExtra(WareInStore.EXTRA_ITEMID, adapter.getItem(position).getId());
                startActivity(intent);
            }
        };
        listView.setOnItemClickListener(itemClickListener);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_in_store);


    }
    private void updateList() {
        Intent intent = getIntent();
        int listID = intent.getIntExtra(EXTRA_LISTNO, 0);

        SQLiteOpenHelper dbhelper = new Miniprojekt1DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query("WARE",
                    new String[]{"_id", "NAME","UNIT","NORMAL_PRICE", "DISCOUNT_PRICE","DISCOUNT_END_DATE","STORE_ID"
                            },
                    "LIST_ID = ?",
                    new String[]{Integer.toString(listID)},
                    null, null, null);
        }
        catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT);
            toast.show();
        }
        finally {
            if(cursor!=null) cursor.close();
            if(db!=null) db.close();
        }
    }
}
