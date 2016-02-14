package ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import model.Category;
import model.Courses;
import model.IndividualAssignment;
import model.user;
import androidstudio.edbud.com.myapplication.R;

public class IndividualCourse extends Activity implements View.OnClickListener{
    private TextView course, unit, letter,gpa;
    private EditText etWeightID, etWeightPercent;
    private ListView assignmentList;
    private Courses mycourse;



    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    ArrayList listDataHeader;
    HashMap<String, Category> listCategory;
    HashMap<String, ArrayList<IndividualAssignment>> listDataChild;
    FloatingActionButton fab2;
    CoordinatorLayout layout_main;
    private PopupWindow popup;
    Button bAddWeight;
    boolean showPercent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_course);
        mycourse = user.myCourse.get(CoursePage.p);
        this.setViews();
        this.setTitle(mycourse.getCourseId());

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listCategory,listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                String weight = listDataHeader.get(groupPosition).toString();


                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

    }


    @TargetApi(Build.VERSION_CODES.M)
    private void setViews(){
        course = (TextView) findViewById(R.id.individual_course);
        unit = (TextView) findViewById(R.id.individual_unit);
        letter = (TextView) findViewById(R.id.individual_letter);
        gpa = (TextView) findViewById(R.id.GPA);
        course.setText(mycourse.getCourseId());
        fab2 = (FloatingActionButton) findViewById(R.id.individual_fab);
        fab2.setOnClickListener(this);
        unit.setText(Double.toString(mycourse.getUnit()));

        bAddWeight = (Button) findViewById(R.id.bAddWeights_individual);
        bAddWeight.setOnClickListener(this);

        layout_main= (CoordinatorLayout) findViewById(R.id.individual_course_page);
        layout_main.getForeground().setAlpha(0);
        gpa.setOnClickListener(this);

        if(mycourse.getLetter()) {
            letter.setText("Letter grade");
            gpa.setText(Double.toString(mycourse.getGpa()));
        }
        else{
            letter.setText("PNP");
            if(mycourse.getPass())
                gpa.setText("Pass");
            else
                gpa.setText("No pass");
        }

    }

    private void prepareListData() {
        listDataHeader = mycourse.getWeights();
        listDataChild = mycourse.getAllAssignments();
        listCategory = mycourse.getCategories();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.individual_fab:
                if(mycourse.getCategories().isEmpty()){
                    Toast.makeText(this,"Please add at least one grading distribution first", Toast.LENGTH_LONG).show();
                    return;
                }
                startActivity(new Intent(this, AddAssignment.class));
                break;
            case R.id.bAddWeights_individual:
                showPop(this);
                break;
            case R.id.bAddIndividualWeight:
                String weightID = etWeightID.getText().toString();
                String weightPercent = etWeightPercent.getText().toString();
                if(TextUtils.isEmpty(weightID)){
                    Toast.makeText(this,"Please input weight name",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(TextUtils.isEmpty(weightPercent)){
                    Toast.makeText(this,"Please input weight percent", Toast.LENGTH_LONG).show();
                    return;
                }
                int percent = Integer.parseInt(weightPercent);
                if(mycourse.addWeight(weightID,percent)){
                    prepareListData();
                    listAdapter.notifyDataSetChanged();}
                else{
                    Toast.makeText(this,"This weight has already been added", Toast.LENGTH_LONG).show();
                    return;
                }

                layout_main.getForeground().setAlpha(0);
                popup.dismiss();
                break;
            case R.id.bCancelAddIndividualWeight:
                layout_main.getForeground().setAlpha(0);
                popup.dismiss();
                break;
            case R.id.GPA:
                if(!showPercent){
                    gpa.setText(Double.toString(mycourse.getTotalPrecent()) + "%");
                    showPercent = true;
                }else if(mycourse.getLetter()){
                    gpa.setText(Double.toString(mycourse.getGpa()));
                    showPercent = false;
                }else if(mycourse.getPass()){
                    gpa.setText("Pass");
                    showPercent = false;
                }else{
                    gpa.setText("No Pass");
                    showPercent = false;
                }





        }
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, CoursePage.class));
    }

    public void showPop(Activity context){
        // Inflate the popup_layout.xml
        // RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.addWeights);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_add_weights, null);

        // Creating the PopupWindow
        popup = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);
        popup.setOutsideTouchable(false);

        etWeightID= (EditText) layout.findViewById(R.id.etWeightId);
        etWeightPercent = (EditText) layout.findViewById(R.id.etWeightPercent);

        //Dim the background
        layout_main.getForeground().setAlpha(220);


        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);


        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.bAddIndividualWeight);
        Button cancel = (Button) layout.findViewById(R.id.bCancelAddIndividualWeight);
        cancel.setOnClickListener(this);
        close.setOnClickListener(this);

    }

}
