package a15s.android.datamatiker.mini_projekt1;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.IllegalFormatException;

public class TestWaresList extends AppCompatActivity {
    private long list_id;
    private long store_id;
    private int amount = -1;
    private Storage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wares_list);

        if(getIntent().hasExtra("listID")) {
            list_id = (long) getIntent().getExtras().get("listID");
        }
        if(getIntent().hasExtra("storeID")) {
            store_id = (long) getIntent().getExtras().get("storeID");
        }
        storage = new Storage(this);

        init();



    }

    private void init(){
        ListView wareList = (ListView)findViewById(R.id.lwWares);

        Cursor cursor = storage.getWaresByStoreID((int) store_id);
        WareAdapter wareAdapter = new WareAdapter(this, cursor, 0);
        wareList.setAdapter(wareAdapter);

        wareList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long itemID) {
                amountPopUp((int)itemID);
            }
        });

    }
    private void amountPopUp(int itemID){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Input amount");
        final EditText input = new EditText(this);
        b.setView(input);
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try {
                    amount = Integer.parseInt(input.getText().toString());
                    if(amount > 0){
                        //storage.addNoteToWare((int) list_id, amount, itemID, store_id);
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
