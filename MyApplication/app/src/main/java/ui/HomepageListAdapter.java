package ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
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
        TextView dueProjection = (TextView) row.findViewById(R.id.projection);


        dueRow.setText(recentDues.get(position).getAssignmentName());
        dueDateRow.setText(context.getResources().getStringArray(R.array.Month)[recentDues.get(position).getMonth()] + ", "
                + recentDues.get(position).getDay()+", "
                + recentDues.get(position).getYear() );
        dueBelongRow.setText(recentDues.get(position).getBelongsToCourse());

        int id = BaseActivity.initialize.getTerm(BaseActivity.initialize.getCurrTerm()).getTermCourseList().indexOf(recentDues.get(position).getBelongsToCourse());
        ArrayList<IndividualAssignment> temp= BaseActivity.initialize.getTerm(BaseActivity.initialize.getCurrTerm()).getTermCourses().get(id).getCategories().get(recentDues.get(position).getBelongsToCategory()).getAssignments();


        for(int i = 0; i < temp.size(); ++i){
            if(temp.get(i).getAssignmentName().equals(recentDues.get(position).getAssignmentName())){
                if(temp.get(i).getCanReachNextLevel())
                    dueProjection.setText("You need to get at least " + String.format("%.2f",temp.get(i).getPercentToImprove()*100.0) + "% to get to " + temp.get(i).getNextLevel() + "range");
                else
                dueProjection.setText("You need to get at least " + String.format("%.2f",temp.get(i).getPercentToImprove()*100.0) + "% to maintain " + temp.get(i).getNextLevel() + "range");
                break;
            }
        }

        return row;


    }
}
