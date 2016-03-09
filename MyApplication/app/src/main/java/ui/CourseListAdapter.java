package ui;

/**
 * Created by LunaLu on 2/18/16.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import androidstudio.edbud.com.myapplication.R;
import model.Courses;

public class CourseListAdapter extends ArrayAdapter {
    private ArrayList<Courses> courseList = new ArrayList<Courses>();
    Context context;


    public CourseListAdapter(Context context, ArrayList<Courses> course) {

        super(context, R.layout.list_course_page, R.id.list_course_page, course);

        this.context = context;
        this.courseList = course;
    }


    @Override public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.list_course_page, parent, false);
            TextView course = (TextView) row.findViewById(R.id.list_course_page);
            TextView gpa = (TextView) row.findViewById(R.id.list_course_page_gpa);

        course.setTypeface(null, Typeface.BOLD);
        course.setText(courseList.get(position).getCourseId());
        //course.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_Large);
        gpa.setTypeface(null, Typeface.BOLD);
        gpa.setText(String.format("%.2f", courseList.get(position).getGpa()));
        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length); }
}