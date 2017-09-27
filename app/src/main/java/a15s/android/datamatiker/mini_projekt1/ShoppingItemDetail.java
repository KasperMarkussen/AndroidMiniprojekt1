package a15s.android.datamatiker.mini_projekt1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class ShoppingItemDetail extends AppCompatActivity {

    public static final String EXTRA_ITEMID = "mini_projekt1_shopping_item_id";
    private ShoppingItem item;
    SQLiteOpenHelper dbhelper = new Miniprojekt1DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_item_detail);


        refreshView();
    }



    public void onClickMarkButton(View view) {

        if(item.isBought()) item.setBought(false);
        else item.setBought(true);

        item.save(this);

        refreshView();
    }

    private void refreshView() {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor c = null;
        Intent intent = getIntent();
        int itemId = intent.getIntExtra(EXTRA_ITEMID, 0);

        try{
            c = db.query("SHOPPINGITEM",
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
                    "_id = ?",
                    new String[] {Integer.toString(itemId)},
                    null, null, null);

            if(c.moveToFirst()) item = new ShoppingItem(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getInt(4),
                    c.getDouble(5),
                    c.getDouble(6),
                    new Date(c.getLong(7)),
                    c.getString(8),
                    c.getInt(9)>0
            );

            TextView itemText = (TextView) findViewById(R.id.item_detail_nameView);
            itemText.setText(item.getWareName());

            itemText = (TextView) findViewById(R.id.item_detail_priceView);
            StringBuilder sb = new StringBuilder();
            sb.append(item.getPrice()).append(getString(R.string.currency_signature));
            if(item.isDiscountValid()) sb.append('\n').append(getString(R.string.discount_expire)+" "+item.getDiscountEndDate().toString());
            itemText.setText(sb);

            itemText = (TextView) findViewById(R.id.item_detail_amountView);
            itemText.setText(item.getAmount()+" x "+item.getUnit());

            itemText = (TextView) findViewById(R.id.item_detail_storeNameView);
            itemText.setText(item.getStoreName());

            Button btn = (Button) findViewById(R.id.markShoppingItemBoughtBtn);
            if(item.isBought()) btn.setText(R.string.bought);
            else btn.setText(R.string.mark_bought);
        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        finally {
            if(db!=null) db.close();
            if(c!=null) c.close();
        }
    }
}
