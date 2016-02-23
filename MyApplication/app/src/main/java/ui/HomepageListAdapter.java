package ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidstudio.edbud.com.myapplication.R;
import model.IndividualAssignment;

/**
 * Created by LunaLu on 2/16/16.
 */
public class HomepageListAdapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<IndividualAssignment> recentDues;

    HomepageListAdapter(Context c, ArrayList recent){

        super(c, R.layout.list_recent_dues, R.id.due_homepage, recent);

        this.context = c;
        this.recentDues = recent;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_recent_dues, parent, false);
        TextView dueRow = (TextView) row.findViewById(R.id.due_homepage);
        TextView dueDateRow = (TextView) row.findViewById(R.id.dueDate_homepage);
        TextView dueBelongRow = (TextView) row.findViewById(R.id.dueBelowsTo_homepage);

        dueRow.setText(recentDues.get(position).getAssignmentName());
        dueDateRow.setText(recentDues.get(position).getDay() + "-" + recentDues.get(position).getMonth() + "-" + recentDues.get(position).getYear() );
        dueBelongRow.setText(recentDues.get(position).getBelongsTo());

        return row;


    }
}
