package ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidstudio.edbud.com.myapplication.R;
import model.Courses;

/**
 * Created by LunaLu on 2/23/16.
 */
public class AddTermListAdapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<Courses> courseList;
    boolean isFuture;

    AddTermListAdapter(Context c, ArrayList course, boolean isFuture) {

        super(c, R.layout.list_add_term, R.id.txTermCourseId, course);


        this.context = c;
        this.courseList = course;
        this.isFuture = isFuture;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_add_term, parent, false);
        TextView txtTermCourseId = (TextView) row.findViewById(R.id.txTermCourseId);
        TextView txtTermCourseUnit = (TextView) row.findViewById(R.id.txTermCourseUnit);
        TextView txtTermCourseGrade = (TextView) row.findViewById(R.id.txTermCourseGrade);



        txtTermCourseId.setText(courseList.get(position).getCourseId());

        if(isFuture){
            txtTermCourseGrade.setText(Double.toString(courseList.get(position).getUnit()));
            txtTermCourseUnit.setText(" ");
        }
        else {
            txtTermCourseUnit.setText(Double.toString(courseList.get(position).getUnit()));
            txtTermCourseGrade.setText(courseList.get(position).getGrade());
        }



        return row;


    }
}