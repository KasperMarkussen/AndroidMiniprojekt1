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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShoppingListDetail extends AppCompatActivity {

    public static final String EXTRA_LISTNO = "mini_projekt1_shopping_list_no";
    private int list_id;



    private ArrayList<ShoppingItem> shoppingList = new ArrayList<ShoppingItem>();
    private ArrayAdapter<ShoppingItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_detail);

        list_id = getIntent().getIntExtra(ShoppingListDetail.EXTRA_LISTNO, -1);

        updateList();




        Button add = (Button)findViewById(R.id.addItemBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showStores = new Intent(getApplicationContext(), StoreList.class);
                showStores.putExtra(ShoppingListDetail.EXTRA_LISTNO, list_id);
                startActivity(showStores);
            }
        });

        Button delete = (Button)findViewById(R.id.delListBtn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Storage.removeList(getBaseContext(), list_id))
                    finish();

            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        updateList();
    }

    private void updateList() {
        Intent intent = getIntent();
        int listID = intent.getIntExtra(EXTRA_LISTNO, 0);

        SQLiteOpenHelper dbhelper = new Miniprojekt1DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = null;

        try {

            cursor = db.query("SHOPPINGITEM",
                    new String[]{
                            "_id",
                            "WARE_NAME",
                            "UNIT",
                            "AMOUNT",
                            "LIST_ID",
                            "NORMAL_PRICE",
                            "DISCOUNT_PRICE",
                            "DISCOUNT_END_DATE",
                            "STORE_NAME",
                            "BOUGHT"},
                    "LIST_ID = ?",
                    new String[] {Integer.toString(listID)},
                    null, null, null);


            ArrayList<ShoppingItem> list = new ArrayList<>();
            while(cursor.moveToNext())
                list.add(new ShoppingItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getDouble(5),
                        cursor.getDouble(6),
                        new Date(cursor.getLong(7)),
                        cursor.getString(8),
                        cursor.getInt(9)>0
                        ));

            shoppingList = list;

            adapter = new ArrayAdapter<ShoppingItem>(this,
                    android.R.layout.simple_list_item_1,
                    shoppingList);
            ListView listView = (ListView) findViewById(R.id.shoppingListView);
            listView.setAdapter(adapter);

            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ShoppingListDetail.this, ShoppingItemDetail.class);
                    intent.putExtra(ShoppingItemDetail.EXTRA_ITEMID, adapter.getItem(position).getId());
                    startActivity(intent);
                }
            };
            listView.setOnItemClickListener(itemClickListener);

            double total = 0;
            double savedTotal = 0;
            for (ShoppingItem element : shoppingList) {
                total+=element.getPrice();
                savedTotal+=element.getDiscountSize();
            }
            TextView totalTextView = (TextView) findViewById(R.id.totalCostTextView);
            TextView savedTextView = (TextView) findViewById(R.id.totalSavedTextView);
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.CEILING);
            totalTextView.setText("Total "+df.format(total)+"kr");
            savedTextView.setText(df.format(savedTotal)+"kr saved");
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
