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
import java.util.LinkedHashMap;
import java.util.List;

import androidstudio.edbud.com.myapplication.R;
import model.Category;
import model.IndividualAssignment;

/**
 * Created by LunaLu on 2/12/16.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private ArrayList<String> weightsList; // header titles
        // child data in format of header title, child title
        private HashMap<String, ArrayList<IndividualAssignment>> _listDataChild;
        private HashMap<String, Category> _listCategory;
        private LinkedHashMap<String, Category> categories;
        public ExpandableListAdapter(Context context, ArrayList<String> weights, LinkedHashMap <String, Category> c) {
            this._context = context;
            this.weightsList = weights;
            this.categories = c;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this.categories.get(this.weightsList.get(groupPosition)).getAssignments().get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            IndividualAssignment child = (IndividualAssignment) getChild(groupPosition, childPosition);

            final String childText = child.getAssignmentName();
            double childRawScore = child.getRawScore();
            double childScoreOutOf = child.getScoreOutOf();

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_individual_assignment, parent,false);
            }

            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.lblListIndividualAssignment);

            TextView gradeText = (TextView) convertView.findViewById(R.id.lblListIndividualGrade);

            TextView duedateText = (TextView) convertView.findViewById(R.id.lblListIndividualDueDate);
            int year, month,day;
            year = child.getYear();
            month = child.getMonth();
            day = child.getDay();
            Resources rec = _context.getResources();
            duedateText.setText(rec.getStringArray(R.array.Month)[month] + ", "+day+", "+year);
            if(child.isSetScore()) {
                gradeText.setText(Double.toString(childRawScore) + " / " + Double.toString(childScoreOutOf));
                txtListChild.setTypeface(null, Typeface.NORMAL);
            }
            else {
                gradeText.setText(" ");
                txtListChild.setTypeface(null, Typeface.BOLD);
            }
            txtListChild.setText(childText);
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            if(this.categories.get(this.weightsList.get(groupPosition)).getAssignments() == null){
                    return 0;
            }
            return this.categories.get(this.weightsList.get(groupPosition)).getAssignments().size();
        }

        @Override
        public Category getGroup(int groupPosition) {
            return this.categories.get(this.weightsList.get(groupPosition));
        }

        @Override
        public int getGroupCount() {
            if(weightsList == null)
                return 0;
            return this.weightsList.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            Category header = getGroup(groupPosition);
            String headerTitle = header.getCategoryName();
            String headerPercent = "0.0";
                if(header.isScoreInputted()) {
                    headerPercent =String.format("%.2f",header.getCurrPercent()*100);
                }

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

            lblListHeaderPercent.setText(headerPercent + "%");


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

