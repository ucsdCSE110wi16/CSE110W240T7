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

    AddTermListAdapter(Context c, ArrayList course) {

        super(c, R.layout.list_add_term, R.id.txTermCourseId, course);


        this.context = c;
        this.courseList = course;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_add_term, parent, false);
        TextView txtTermCourseId = (TextView) row.findViewById(R.id.txTermCourseId);
        TextView txtTermCourseUnit = (TextView) row.findViewById(R.id.txTermCourseUnit);
        TextView txtTermCourseGrade = (TextView) row.findViewById(R.id.txTermCourseGrade);

        txtTermCourseId.setText(courseList.get(position).getCourseId());
        txtTermCourseUnit.setText(Double.toString(courseList.get(position).getUnit()));
        double gpa = courseList.get(position).getGpa();
        String grade;
        if(gpa > 3.0)
            grade = "A";
        else if(gpa > 2.0)
            grade = "B";
        else if(gpa > 1.0)
            grade = "C";
        else if(gpa > 0.0)
            grade = "D";
        else
            grade = "F";

        txtTermCourseGrade.setText(grade);



        return row;


    }
}