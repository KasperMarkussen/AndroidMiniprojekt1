package a15s.android.datamatiker.mini_projekt1;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Sonnich on 01-10-2017.
 */

public class WareAdapter extends CursorAdapter{
    private LayoutInflater cursorInflater;
    private View.OnClickListener listener;

    public WareAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.ware_skab, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int id = cursor.getInt(0);
        TextView txtName = (TextView)view.findViewById(R.id.txtWareName);
        txtName.setText(cursor.getString(1));
        txtName.setTag(id);

        TextView txtUnit = (TextView)view.findViewById(R.id.txtWareUnit);
        txtUnit.setText(cursor.getString(2));
        txtUnit.setTag(id);

        TextView txtPrice = (TextView)view.findViewById(R.id.txtWarePrice);
        txtPrice.setText(cursor.getString(3));
        txtPrice.setTag(id);

        TextView txtDisc = (TextView)view.findViewById(R.id.txtWareDiscount);
        txtDisc.setText(cursor.getString(4));
        txtDisc.setTag(id);

    }
}
