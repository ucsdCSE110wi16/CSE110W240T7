package model;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
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

import com.firebase.client.Firebase;
import com.firebase.client.collection.LLRBNode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import androidstudio.edbud.com.myapplication.R;
import ui.AddAssignment;
import ui.BaseActivity;
import ui.CoursePage;
import ui.ExpandableListAdapter;

public class IndividualCourse extends Activity implements View.OnClickListener{
	private TextView course, unit, letter,gpa,highest;
	private EditText etWeightID, etWeightPercent, etRawScore, etScoreOutOf;
	private ListView assignmentList;
	private Courses mycourse = BaseActivity.initialize.getTerm(BaseActivity.initialize.getCurrTerm()).getTermCourses().get(CoursePage.p);
	private Context myContext;
	private int i = 2;



	public static ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	ArrayList<String> listDataHeader;
	LinkedHashMap<String, Category> listCategory;
	FloatingActionButton fab2;
	CoordinatorLayout layout_main;
	private PopupWindow popup;
	int index;
	String weight;
	Button bAddWeight;
	boolean showPercent = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		double myGPA = BaseActivity.initialize.getTerm(BaseActivity.initialize.getCurrTerm()).getTermCourses().get(CoursePage.p).getGpa();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_individual_course);
		myContext = this;
		if(BaseActivity.initialize.getTerm(BaseActivity.initialize.getCurrTerm()).getTermCourses().get(CoursePage.p) == null){
			Log.v("p value is :", String.valueOf(CoursePage.p));
            Log.v("my course:", "is null");
		}

		this.setViews();
		this.setTitle(mycourse.getCourseId());
		listDataHeader = mycourse.getWeightsList();

        // get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
		listCategory = mycourse.getCategories();


		listAdapter = new ExpandableListAdapter(this, listDataHeader, listCategory);

        // setting list adapter
		expListView.setAdapter(listAdapter);


		expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {

				weight = listDataHeader.get(groupPosition).toString();
				index = childPosition;
				if (mycourse.getCategories().get(weight).getAssignments().get(index).isSetScore()) {
                    //show popup asking if the User wants to change the score already inputted
					new AlertDialog.Builder(myContext).setMessage("You have already inputted score for " +
						mycourse.getCategories().get(weight).getAssignments().get(index).getAssignmentName() +
						". Are you sure you want to change? ").setNegativeButton("Cancel", null).setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which){
								showSetScore((Activity) myContext, true);
							}

						}).show();
					}
					else
						showSetScore((Activity) myContext, false);
                                    return false;
                                }
                            }

                            );

	}


	@TargetApi(Build.VERSION_CODES.M)
	private void setViews(){
		course = (TextView) findViewById(R.id.individual_course);
		unit = (TextView) findViewById(R.id.individual_unit);

		letter = (TextView) findViewById(R.id.individual_letter);
		gpa = (TextView) findViewById(R.id.GPA);
		highest = (TextView) findViewById(R.id.individual_highestGradePossible);
		course.setText(mycourse.getCourseId());


		double myGPA = BaseActivity.initialize.getTerm(BaseActivity.initialize.getCurrTerm()).getTermCourses().get(CoursePage.p).getGpa();

		gpa = (TextView) findViewById(R.id.GPA);
		if(myGPA == 4.0 ) gpa.setTextColor(Color.rgb(60,179,113));
		else if(myGPA>=3.0) gpa.setTextColor(Color.rgb(255,215,0));
		else if(myGPA>2.0) gpa.setTextColor(Color.rgb(255,165,0));
		else gpa.setTextColor(Color.rgb(255,69,0));



		course.setText(BaseActivity.initialize.getTerm(BaseActivity.initialize.getCurrTerm()).getTermCourses().get(CoursePage.p)
			.getCourseId());
		fab2 = (FloatingActionButton) findViewById(R.id.individual_fab);
		fab2.setOnClickListener(this);
		unit.setText(String.format("%.2f", mycourse.getUnit()));
		highest.setText(mycourse.getHighestGradePossible());

		bAddWeight = (Button) findViewById(R.id.bAddWeights_individual);
		bAddWeight.setOnClickListener(this);

		layout_main= (CoordinatorLayout) findViewById(R.id.individual_course_page);
		layout_main.getForeground().setAlpha(0);
		gpa.setOnClickListener(this);

		if(mycourse.getLetter()) {
			letter.setText("Letter grade");
			gpa.setText(String.format("%.2f", mycourse.getGpa()));
		}
		else{
			letter.setText("PNP");
			if(mycourse.getPass())
				gpa.setText("Pass");
			else
				gpa.setText("No pass");
		}

	}

	private void prepareData(){
		System.out.println("updated");
		listDataHeader = mycourse.getWeightsList();
		listCategory = mycourse.getCategories();
		listAdapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
			case R.id.individual_fab:
			if(listCategory.isEmpty()){
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
			if(mycourse.addWeight(weightID, percent)){
				BaseActivity.initialize.getTerm(BaseActivity.initialize.getCurrTerm()).getTermCourses().get(CoursePage.p).addWeight(weightID, percent);
				System.out.println("add new weight: " + weightID);
				Firebase start = new Firebase("https://edbud.firebaseio.com/userInfo/" + BaseActivity.initialize.uid);
				start.setValue(BaseActivity.initialize);
				highest.setText(mycourse.getHighestGradePossible());
                   // listDataHeader.add(weightID);

				prepareData();
			}
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
			case R.id.bSetScore:
			String rawScore = etRawScore.getText().toString();
			String ScoreOutOf = etScoreOutOf.getText().toString();
			if(TextUtils.isEmpty(rawScore)){
				Toast.makeText(this,"Please input raw score",Toast.LENGTH_LONG).show();
				return;
			}
			else if(TextUtils.isEmpty(ScoreOutOf)){
				Toast.makeText(this,"Please input Score out of", Toast.LENGTH_LONG).show();
				return;
			}
			int r = Integer.parseInt(rawScore);
			int s = Integer.parseInt(ScoreOutOf);
			double gpanumber = BaseActivity.initialize.getTerm(BaseActivity.initialize.getCurrTerm()).getTermCourses().get(CoursePage.p).addAssignmentScore(weight, index, r, s);
			
			prepareData();
			if(gpanumber == 4.0 ) gpa.setTextColor(Color.rgb(60,179,113));
			else if(gpanumber>=3.0) gpa.setTextColor(Color.rgb(255,215,0));
			else if(gpanumber>2.0) gpa.setTextColor(Color.rgb(255,165,0));
			else gpa.setTextColor(Color.rgb(255,69,0));

			Firebase start = new Firebase("https://edbud.firebaseio.com/userInfo/" + BaseActivity.initialize.uid);
			start.setValue(BaseActivity.initialize);
			gpa.setText(Double.toString(mycourse.getGpa()));
			highest.setText(mycourse.getHighestGradePossible());
			layout_main.getForeground().setAlpha(0);
			popup.dismiss();
			System.out.println("after dismiss popup");
			break;
			case R.id.bCancelSetScore:
			layout_main.getForeground().setAlpha(0);
			popup.dismiss();
			case R.id.GPA:
			if(i == 0){
				gpa.setText(String.format("%.2f", mycourse.getTotalPercent()) + "%");
                    //showPercent = true;
				++i;
			}else if(i == 1){
				if(BaseActivity.initialize.getTerm(BaseActivity.initialize.getCurrTerm()).getTermCourses().get(CoursePage.p).getLetter()){
					gpa.setText(String.format("%.2f", mycourse.getGpa()));
					showPercent = false;
				}else if(mycourse.getPass()){
					gpa.setText("Pass");
					showPercent = false;
				}else {
					gpa.setText("No Pass");
					showPercent = false;
				}
				++i;
			}
			else{
				gpa.setText(mycourse.getGrade());
				i = 0;
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

	public void showSetScore(Activity context, boolean hasSetted) {
        // Inflate the popup_layout.xml
        // RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.addWeights);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_set_score, null);

        // Creating the PopupWindow
        popup = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);
        popup.setOutsideTouchable(false);

        etRawScore = (EditText) layout.findViewById(R.id.etRawScore);
        etScoreOutOf = (EditText) layout.findViewById(R.id.etScoreOutOf);
        TextView assignmentName = (TextView) layout.findViewById(R.id.textview_assignment_name);
        assignmentName.setText(BaseActivity.initialize.getTerm(BaseActivity.initialize.getCurrTerm()).getTermCourses().get(CoursePage.p).getCategories().get(weight).getAssignments().get(index).getAssignmentName());
        if (hasSetted) {
            etRawScore.setHint(Double.toString(BaseActivity.initialize.getTerm(BaseActivity.initialize.getCurrTerm()).getTermCourses().get(CoursePage.p).getCategories().get(weight).getAssignments().get(index).getRawScore()));
            etScoreOutOf.setHint(Double.toString(BaseActivity.initialize.getTerm(BaseActivity.initialize.getCurrTerm()).getTermCourses().get(CoursePage.p).getCategories().get(weight).getAssignments().get(index).getScoreOutOf()));
        }

        //Dim the background
        layout_main.getForeground().setAlpha(220);


        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);


        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.bSetScore);
        Button cancel = (Button) layout.findViewById(R.id.bCancelSetScore);
        cancel.setOnClickListener(this);
        close.setOnClickListener(this);

    }

}
