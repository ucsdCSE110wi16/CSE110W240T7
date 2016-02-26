package ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.deser.Deserializers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import androidstudio.edbud.com.myapplication.R;
import login.Login;
import model.Category;
import model.Courses;
import model.IndividualCourse;
import model.Term;

/**
 * Created by LunaLu on 2/19/16.
 * Floating action button Source: @Mark Allison https://bitbucket.org/StylingAndroid/floatingactionbutton/src/84153529d82ccbab1c6a11d971c03a165d0440ee?at=Part2
 */
public class FourYearPlan extends BaseActivity implements View.OnClickListener{

    FourYearPlanAdapter myAdapter;
    FourYearPlanListView listView;
    ImageButton fab;
    View fabAction1, fabAction2;
    boolean expanded = false;
    float offset1,offset2;
    Context context;
    Activity activity;
    TextView txAddTerm, txAddCourse;
    private Button bA, bB, bC, bD,bF;
    private EditText etTermCourseId, etTermCourseUnit;
    private RelativeLayout layout_main;
    private PopupWindow popup;
    private double courseGpa=0.0;
    private int courseUnit = 0;
    private Switch gradeSwitch;
    private boolean letter = true;
    private TextView switchStatus, termUnit, termGpa;
    private RadioGroup termsGroup;
    private RadioButton termButton;

    private ArrayList termList;
    private LinkedHashMap<String, Term> fourYearList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4year);
        super.onCreateNavigation();
        context = this;
        activity = this;

        layout_main = (RelativeLayout) findViewById(R.id.fourYearPlan);
        layout_main.getForeground().setAlpha(0);
        listView = (FourYearPlanListView) findViewById(R.id.lsTerms);
        txAddCourse = (TextView) findViewById(R.id.txAddCourse);
        txAddTerm = (TextView) findViewById(R.id.txAddTerm);
        txAddCourse.setVisibility(View.GONE);
        txAddTerm.setVisibility(View.GONE);
        final ViewGroup fabContainer = (ViewGroup) findViewById(R.id.fab_container);
        fab = (ImageButton) findViewById(R.id.fab);
        fabAction1 = findViewById(R.id.fab_action_1);
        fabAction2 = findViewById(R.id.fab_action_2);
        fabAction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddTerm.class));
            }
        });
        fabAction2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop(activity);
               // startActivity(new Intent(context, IndividualCourse.class));
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expanded = !expanded;
                if (expanded) {
                    expandFab();
                } else {
                    collapseFab();
                }
            }
        });
        fabContainer.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                fabContainer.getViewTreeObserver().removeOnPreDrawListener(this);
                offset1 = fab.getY() - fabAction1.getY();
                fabAction1.setTranslationY(offset1);
                offset2 = fab.getY() - fabAction2.getY();
                fabAction2.setTranslationY(offset2);
                return true;
            }
        });

        termList = BaseActivity.initialize.getTerms();
        fourYearList = BaseActivity.initialize.getMy4YearPlan();
        myAdapter = new FourYearPlanAdapter(this, termList, fourYearList);
        listView.setDragOnLongPress(true);
        // setting list adapter
        listView.setAdapter(myAdapter);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bpopupAddPastCourse:
                String courseId = etTermCourseId.getText().toString();
                String courseUnitString = etTermCourseUnit.getText().toString();
                int selectedId = termsGroup.getCheckedRadioButtonId();

                if(TextUtils.isEmpty(courseId)){
                    Toast.makeText(this, "Please input course name", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(TextUtils.isEmpty(courseUnitString)){
                    Toast.makeText(this,"Please input course unit",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(courseGpa == 0.0){
                    Toast.makeText(this, "Please choose course grade", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(selectedId == -1){
                    Toast.makeText(this, "Please select a term", Toast.LENGTH_LONG).show();
                    return;
                }

                termButton = (RadioButton) termsGroup.findViewById(selectedId);
                courseUnit = Integer.parseInt(courseUnitString);
                BaseActivity.initialize.getTerm(termButton.getText().toString()).addTermCourses(new Courses(courseId, courseUnit, letter, courseGpa));
                layout_main.getForeground().setAlpha(0);
                popup.dismiss();
                myAdapter.notifyDataSetChanged();
                listView.invalidate();
                break;
            case R.id.bpopupCancelAddPastCourse:
                layout_main.getForeground().setAlpha(0);
                popup.dismiss();
                break;
            case R.id.bA_course:
                courseGpa = 4.0;
                changeButtonColor("A");
                break;
            case R.id.bB_course:
                if(letter)
                    courseGpa = 0.0;
                courseGpa = 3.0;
                changeButtonColor("B");
                break;
            case R.id.bC_course:
                courseGpa = 2.0;
                changeButtonColor("C");
                break;
            case R.id.bD_course:
                courseGpa = 1.0;
                changeButtonColor("D");
                break;
            case R.id.bF_course:
                courseGpa = 0.0;
                changeButtonColor("F");
                break;

        }
    }

    private void collapseFab() {
        fab.setImageResource(R.drawable.animated_minus);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(createCollapseAnimator(fabAction1, offset1),
                createCollapseAnimator(fabAction2, offset2),createCollapseAnimator(txAddCourse, offset2), createCollapseAnimator(txAddTerm, offset1));
        animatorSet.start();
        animateFab();
        txAddTerm.setVisibility(View.GONE);
        txAddCourse.setVisibility(View.GONE);
    }

    private void expandFab() {
        fab.setImageResource(R.drawable.animated_plus);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(createExpandAnimator(fabAction1, offset1),
                createExpandAnimator(fabAction2, offset2),createExpandAnimator(txAddCourse, offset2), createExpandAnimator(txAddTerm, offset1) );
        animatorSet.start();
        animateFab();
        txAddTerm.setVisibility(View.VISIBLE);
        txAddCourse.setVisibility(View.VISIBLE);

    }

    private static final String TRANSLATION_Y = "translationY";

    private Animator createCollapseAnimator(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, 0, offset)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private Animator createExpandAnimator(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, offset, 0)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private void animateFab() {
        Drawable drawable = fab.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }


    }

    public void showPop(Activity context){
        // Inflate the popup_layout.xml
        // RelativeLayout viewGroup = (RelativeLayout) context.findViewById(R.id.addWeights);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_add_course, null);

        // Creating the PopupWindow
        popup = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);
        popup.setOutsideTouchable(false);

        etTermCourseId= (EditText) layout.findViewById(R.id.etPastCourseName);
        etTermCourseUnit = (EditText) layout.findViewById(R.id.etPastCourseUnit);
        bA = (Button) layout.findViewById(R.id.bA_course);
        bB = (Button) layout.findViewById(R.id.bB_course);
        bC = (Button) layout.findViewById(R.id.bC_course);
        bD = (Button) layout.findViewById(R.id.bD_course);
        bF = (Button) layout.findViewById(R.id.bF_course);
        bA.setOnClickListener(this);
        bB.setOnClickListener(this);
        bC.setOnClickListener(this);
        bD.setOnClickListener(this);
        bF.setOnClickListener(this);
        gradeSwitch = (Switch) layout.findViewById(R.id.letterSwitch_course);
        switchStatus = (TextView) layout.findViewById(R.id.letterSwitchStatus_course);
        termsGroup = (RadioGroup) layout.findViewById(R.id.radioTerms);
        RadioButton rdbtn;
        ArrayList terms = BaseActivity.initialize.getTerms();
        for (int i = 0; i<terms.size(); ++i) {
            rdbtn = new RadioButton(this);
            rdbtn.setId(i);
            rdbtn.setText(terms.get(i).toString());
            termsGroup.addView(rdbtn);
        }

        gradeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchStatus.setText("Pass/No Pass");
                    letter = false;
                    bA.setText("P");
                    bB.setText("NP");
                    bC.setVisibility(View.GONE);
                    bD.setVisibility(View.GONE);
                    bF.setVisibility(View.GONE);
                } else {
                    switchStatus.setText("Letter grade");
                    letter = true;
                    bA.setText("A");
                    bB.setText("B");
                    bC.setVisibility(View.VISIBLE);
                    bD.setVisibility(View.VISIBLE);
                    bF.setVisibility(View.VISIBLE);
                }
            }
        });



        //Dim the background
        layout_main.getForeground().setAlpha(220);


        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);


        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.bpopupAddPastCourse);
        Button cancel = (Button) layout.findViewById(R.id.bpopupCancelAddPastCourse);
        cancel.setOnClickListener(this);
        close.setOnClickListener(this);

    }
    private void changeButtonColor(String button){
        switch(button){
            case "A":
                bA.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bB.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "B":
                bB.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bA.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "C":
                bC.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bB.setBackgroundColor(Color.TRANSPARENT);
                bA.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "D":
                bD.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bB.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bA.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "F":
                bF.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bB.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bA.setBackgroundColor(Color.TRANSPARENT);
                break;
        }
    }
}
