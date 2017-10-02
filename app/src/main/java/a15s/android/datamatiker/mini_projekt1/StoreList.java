package a15s.android.datamatiker.mini_projekt1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;


public class StoreList extends AppCompatActivity {

    private Storage storage;
    private ArrayList<Store> stores = new ArrayList<Store>();
    //private ArrayAdapter<Store> adapter;
    private int list_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);

        list_id = getIntent().getIntExtra(ShoppingListDetail.EXTRA_LISTNO, -1);

        ListView lView = (ListView) findViewById(R.id.storeListView);
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, Storage.getStores(this), new String[] {"NAME"}, new int[]{android.R.id.text1});

        lView.setAdapter(adapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), TestWaresList.class);
                intent.putExtra(ShoppingListDetail.EXTRA_LISTNO, list_id);
                intent.putExtra(TestWaresList.EXTRA_STORE_ID, (int) id );
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
}
