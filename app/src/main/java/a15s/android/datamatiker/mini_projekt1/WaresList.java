package a15s.android.datamatiker.mini_projekt1;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.IllegalFormatException;

public class WaresList extends AppCompatActivity {
    public static final String EXTRA_STORE_ID = "mini_projekt1_store_id";
    private int list_id;
    private int store_id;
    private int amount = -1;
    private Storage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wares_list);

        list_id = getIntent().getIntExtra(ShoppingListDetail.EXTRA_LISTNO, -1);
        store_id = getIntent().getIntExtra(WaresList.EXTRA_STORE_ID, -1);

        init();



    }

    private void init(){
        ListView wareList = (ListView)findViewById(R.id.lwWares);

        Cursor cursor = Storage.getWaresByStoreID(this, (int) store_id);
        WareAdapter wareAdapter = new WareAdapter(this, cursor, 0);
        wareList.setAdapter(wareAdapter);

        wareList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long itemID) {
                amountPopUp(parent.getContext(), (int)itemID);
            }
        });

    }
    private void amountPopUp(final Context context, final int itemID){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Input amount");
        final EditText input = new EditText(this);
        input.setKeyListener(new DigitsKeyListener().getInstance("0123456789"));
        b.setView(input);
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try {
                    amount = Integer.parseInt(input.getText().toString());
                    if(amount > 0){
                        storage.addItemToList(context, (int) list_id, amount, itemID, store_id);
                        finish();
                    }
                } catch (IllegalFormatException e){
                    Toast.makeText(getBaseContext(), "Bad input", Toast.LENGTH_SHORT).show();
                }
            }
        });
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog popup = b.create();
        popup.show();
    }

}
