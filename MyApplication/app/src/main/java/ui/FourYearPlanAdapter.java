package ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidstudio.edbud.com.myapplication.R;
import model.Category;
import model.Courses;
import model.IndividualAssignment;
import model.Term;

/**
 * Created by LunaLu on 2/19/16.
 */
public class FourYearPlanAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    
    // child data in format of header title, child title
    private HashMap<String, Term> _listDataChild;
    private int selectedGroup;
    private int selectedChild;
    DecimalFormat df = new DecimalFormat("#.##");


    public FourYearPlanAdapter(Context context, ArrayList<String> listDataHeader, HashMap<String, Term> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    public void onPick(int[] position) {
        selectedGroup = position[0];
        selectedChild = position[1];
    }

    public void onDrop(int[] from, int[] to) {
        if (to[0] > _listDataChild.size() || to[0] < 0 || to[1] < 0)
            return;
        /*String tValue = getValue(from);
        children.get(children.keySet().toArray()[from[0]]).remove(tValue);
        children.get(children.keySet().toArray()[to[0]]).add(to[1], tValue);
        selectedGroup = -1;
        selectedChild = -1;*/
        notifyDataSetChanged();
    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).getTermCourses().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        Courses child = (Courses) getChild(groupPosition, childPosition);

        String courseId = child.getCourseId();
        Double courseGpa = child.getGpa();


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_4year_courses, parent,false);
        }

        TextView txtCourse = (TextView) convertView
                .findViewById(R.id.lblListTermCourses);

        TextView txtCourseGpa = (TextView) convertView.findViewById(R.id.lblListTermCourseGpa);

        txtCourse.setText(courseId);
        txtCourseGpa.setText(df.format(courseGpa));
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(this._listDataChild.get(this._listDataHeader.get(groupPosition)) == null){
            return 0;
        }
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).getTermCourses().size();
    }

    @Override
    public String getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_category, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListCategory);
        TextView lblListHeaderPercent = (TextView) convertView.findViewById(R.id.lblListCategoryPercent);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        lblListHeaderPercent.setTypeface(null, Typeface.BOLD);
        lblListHeaderPercent.setText(df.format(_listDataChild.get(headerTitle).getTermGpa()));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
