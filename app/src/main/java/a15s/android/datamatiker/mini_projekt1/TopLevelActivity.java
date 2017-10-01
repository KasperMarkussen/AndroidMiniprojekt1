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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TopLevelActivity extends AppCompatActivity {

    private ArrayList<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();
    private ArrayAdapter<ShoppingList> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        updateList();
        
        adapter=new ArrayAdapter<ShoppingList>(this,
                android.R.layout.simple_list_item_1,
                shoppingLists);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TopLevelActivity.this, ShoppingListDetail.class);
                intent.putExtra(ShoppingListDetail.EXTRA_LISTNO, adapter.getItem(position).getId());
                intent.putExtra("listID", id );
                startActivity(intent);
            }
        };
        listView.setOnItemClickListener(itemClickListener);
    }

    public void onClickAddButton(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        if(editText.getText().length() > 0) {
            ShoppingList newEntry = new ShoppingList(editText.getText().toString());
            shoppingLists.add(newEntry);
            adapter.notifyDataSetChanged();
        }
    }

    private void updateList() {
        Cursor cursor = null;
        SQLiteOpenHelper dbhelper = new Miniprojekt1DatabaseHelper(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        try {
            cursor = db.query("SHOPPINGLIST",
                                    new String[]{"_id", "NAME"},
                                    null, null, null, null, null);
            ArrayList<ShoppingList> list = new ArrayList<>();

            while(cursor.moveToNext()) list.add(new ShoppingList(cursor.getString(1), cursor.getInt(0)));

            shoppingLists = list;
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
