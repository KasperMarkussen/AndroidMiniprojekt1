package a15s.android.datamatiker.mini_projekt1;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Marcus on 02-10-2017.
 */

public class TopLevelListAdapter extends CursorAdapter {
    private LayoutInflater cursorInflater;
    private View.OnClickListener listener;

    public TopLevelListAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.list_template, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtName = (TextView)view.findViewById(R.id.txtListName);
        txtName.setText(cursor.getString(1));
    }
}
