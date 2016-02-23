package ui;

/**
 * Created by LunaLu on 2/18/16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import androidstudio.edbud.com.myapplication.R;
import model.Courses;
import model.user;

public class CardArrayAdapter extends ArrayAdapter {
    private ArrayList<Courses> courseList = new ArrayList<Courses>();
    private ArrayList courseNameList = new ArrayList();
    Context context;


    public CardArrayAdapter(Context context,  ArrayList courseName, ArrayList courses) {

        super(context, R.layout.list_course_page, R.id.list_course_page, courseName);

        this.context = context;
        this.courseNameList = courseName;
        this.courseList = courses;
    }


    @Override public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.list_course_page, parent, false);
            TextView course = (TextView) row.findViewById(R.id.list_course_page);
            TextView gpa = (TextView) row.findViewById(R.id.list_gpa);

        course.setText(courseNameList.get(position).toString());
        gpa.setText(Double.toString(courseList.get(position).getGpa()));
        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length); }
}