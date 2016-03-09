package ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
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

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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

    ImageButton fab;
    View fabAction1, fabAction2;
    boolean expanded = false;
    float offset1,offset2;
    Context context;
    Activity activity;
    TextView txAddTerm, txAddCourse;
    private Button bA, bB, bC, bD,bF,badd,bsub;
    private EditText etTermCourseId, etTermCourseUnit;
    private CoordinatorLayout layout_main;
    private PopupWindow popup;


    private double courseGpa=-1.0;
    private String courseGrade;
    private int courseUnit = 0;
    private Switch gradeSwitch;
    private boolean letter = true;
    private boolean isFuture = true;
    private boolean plus = false;
    private boolean minus = false;

    private TwoFragment twoFragment;
    private OneFragment oneFragment;




    private TextView switchStatus;
    private RadioGroup termsGroup;
    private RadioButton termButton;


    //Tab stuff
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4year);
        super.onCreateNavigation();
        context = this;
        activity = this;
        layout_main = (CoordinatorLayout) findViewById(R.id.fourYearPlan);
        layout_main.getForeground().setAlpha(0);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        oneFragment = new OneFragment();
        oneFragment.setContext(this);
        oneFragment.setFourYearPlanView(this);
        adapter.addFragment(oneFragment, "Future");
        twoFragment = new TwoFragment();
        twoFragment.setContext(this);
        twoFragment.setFourYearPlanView(this);

        adapter.addFragment(twoFragment, "Past");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
                else if(courseGpa < 0.0 && !isFuture){
                    Toast.makeText(this, "Please choose course grade", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(selectedId == -1){
                    Toast.makeText(this, "Please select a term", Toast.LENGTH_LONG).show();
                    return;
                }
                termButton = (RadioButton) termsGroup.findViewById(selectedId);


                System.out.println("gpa: " + courseGpa);

                if(!isFuture) {
                    if (courseGpa == 4.3) {
                        courseGrade = "A+";
                        courseGpa = 4.0;
                    } else if (courseGpa == 3.7) {
                        courseGrade = "A-";
                    } else if (courseGpa == 3.3) {
                        courseGrade = "B+";
                    } else if (courseGpa == 2.7) {
                        courseGrade = "B-";
                    } else if (courseGpa == 2.3) {
                        courseGrade = "C+";
                    } else if (courseGpa == 1.7) {
                        courseGrade = "C-";
                    } else if (courseGrade.equals("D")) {
                        courseGpa = 1.0;
                    } else if (courseGrade.equals("F")) {
                        courseGpa = 0.0;
                    } else if (courseGrade.equals("P")) {
                        courseGpa = 4.0;
                    } else if (courseGrade.equals("NP")) {
                        courseGpa = 0.0;
                    }
                }

                courseUnit = Integer.parseInt(courseUnitString);
                if(!isFuture)
                    BaseActivity.initialize.getTerm(termButton.getText().toString()).addTermCourses(new Courses(courseId, courseUnit, letter, courseGpa, courseGrade));
                else
                    BaseActivity.initialize.getTerm(termButton.getText().toString()).addTermCourses(new Courses(courseId, courseUnit));

                layout_main.getForeground().setAlpha(0);
                popup.dismiss();
                //oneFragment.oneAdapter.notifyDataSetChanged();
                //oneFragment.oneAdapter.notifyDataSetInvalidated();
                //twoFragment.twoAdapter.notifyDataSetChanged();
                //twoFragment.twoAdapter.notifyDataSetInvalidated();
                final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.detach(oneFragment).attach(oneFragment).detach(twoFragment).attach(twoFragment).commit();
                break;
            case R.id.bpopupCancelAddPastCourse:
                layout_main.getForeground().setAlpha(0);
                popup.dismiss();
                break;
            case R.id.bA_course:
                courseGpa = 4.0;
                if(letter)
                    courseGrade = "A";
                else
                    courseGrade = "P";
                changeButtonColor("A");
                plus = false;
                minus = false;
                break;
            case R.id.bB_course:
                if(!letter) {
                    courseGpa = 0.0;
                    courseGrade= "NP";
                }
                else {
                    courseGpa = 3.0;
                    courseGrade = "B";
                }
                changeButtonColor("B");
                plus = false;
                minus = false;
                break;
            case R.id.bC_course:
                courseGpa = 2.0;
                courseGrade = "C";
                changeButtonColor("C");
                plus = false;
                minus = false;
                break;
            case R.id.bD_course:
                courseGpa = 1.0;
                courseGrade = "D";
                changeButtonColor("D");
                plus = false;
                minus = false;
                break;
            case R.id.bF_course:
                courseGpa = 0.0;
                courseGrade = "F";
                changeButtonColor("F");
                plus = false;
                minus = false;
                break;
            case R.id.bplus:

                System.out.println("plus clicked");
                if(minus)
                    courseGpa +=0.6;
                else if (!plus)
                    courseGpa += .3;
                changeButtonColor("Plus");
                plus = true;
                break;
            case R.id.bminus:
                System.out.println("minus clicked");
                if(plus)
                    courseGpa -=0.6;
                else if(!minus)
                    courseGpa -=0.3;
                changeButtonColor("Minus");
                minus = true;
                break;
        }
    }

    private static final String TRANSLATION_Y = "translationY";
    private static final String TRANSLATION_X = "translationX";


    private Animator createCollapseAnimatorY(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, 0, offset)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private Animator createCollapseAnimatorX(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_X, 0, offset)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private Animator createExpandAnimatorY(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, offset, 0)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private Animator createExpandAnimatorX(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_X, offset, 0)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private void animateFab() {
        Drawable drawable = fab.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }


    }

    public void showPop(boolean isFuture){
        this.isFuture = isFuture;
        showPop(activity, isFuture);
    }

    public void showPop(Activity context, boolean isFuture){
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
        badd = (Button) layout.findViewById(R.id.bplus);
        bsub = (Button) layout.findViewById(R.id.bminus);

        gradeSwitch = (Switch) layout.findViewById(R.id.letterSwitch_course);
        switchStatus = (TextView) layout.findViewById(R.id.letterSwitchStatus_course);
        switchStatus.setText("Letter grade");

        termsGroup = (RadioGroup) layout.findViewById(R.id.radioTerms);
        RadioButton rdbtn;

        if(!isFuture) {
            bA.setOnClickListener(this);
            bB.setOnClickListener(this);
            bC.setOnClickListener(this);
            bD.setOnClickListener(this);
            bF.setOnClickListener(this);
            badd.setOnClickListener(this);
            bsub.setOnClickListener(this);

            switchStatus.setText("Letter grade");
            letter = true;

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
                        badd.setVisibility(View.GONE);
                        bsub.setVisibility(View.GONE);
                        courseGpa = -1.0;
                        changeButtonColor("Reset");

                    } else {
                        switchStatus.setText("Letter grade");
                        letter = true;
                        bA.setText("A");
                        bB.setText("B");
                        bC.setVisibility(View.VISIBLE);
                        bD.setVisibility(View.VISIBLE);
                        bF.setVisibility(View.VISIBLE);
                        badd.setVisibility(View.VISIBLE);
                        bsub.setVisibility(View.VISIBLE);
                        courseGpa = -1.0;
                        changeButtonColor("Reset");
                    }
                }
            });



            ArrayList terms = BaseActivity.initialize.getPastTerms();
            for (int i = 0; i < terms.size(); ++i) {
                rdbtn = new RadioButton(this);
                rdbtn.setId(i);
                rdbtn.setText(terms.get(i).toString());
                System.out.println(terms.get(i).toString());
                termsGroup.addView(rdbtn);
            }
        }
        else
        {
            bA.setVisibility(View.GONE);
            bB.setVisibility(View.GONE);
            bC.setVisibility(View.GONE);
            bD.setVisibility(View.GONE);
            bF.setVisibility(View.GONE);
            badd.setVisibility(View.GONE);
            bsub.setVisibility(View.GONE);
            gradeSwitch.setVisibility(View.GONE);
            switchStatus.setVisibility(View.GONE);
            TextView grade = (TextView) layout.findViewById(R.id.TermCourseGradeTitle_course);
            grade.setVisibility(View.GONE);

            ArrayList terms = BaseActivity.initialize.getFutureTerms();
            for (int i = 0; i < terms.size(); ++i) {
                rdbtn = new RadioButton(this);
                rdbtn.setId(i);
                rdbtn.setText(terms.get(i).toString());
                termsGroup.addView(rdbtn);
            }
        }


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
                badd.setBackgroundColor(Color.TRANSPARENT);
                bsub.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "B":
                bB.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bA.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                badd.setBackgroundColor(Color.TRANSPARENT);
                bsub.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "C":
                bC.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bB.setBackgroundColor(Color.TRANSPARENT);
                bA.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                badd.setBackgroundColor(Color.TRANSPARENT);
                bsub.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "D":
                bD.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bB.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bA.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                badd.setBackgroundColor(Color.TRANSPARENT);
                bsub.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "F":
                bF.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bB.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bA.setBackgroundColor(Color.TRANSPARENT);
                badd.setBackgroundColor(Color.TRANSPARENT);
                bsub.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "Plus":
                badd.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                bsub.setBackgroundColor(Color.TRANSPARENT);
                break;
            case "Minus":
                bsub.setBackgroundColor(getColor(R.color.colorPrimaryLight));
                badd.setBackgroundColor(Color.TRANSPARENT);
                break;
            default:
                bsub.setBackgroundColor(Color.TRANSPARENT);
                badd.setBackgroundColor(Color.TRANSPARENT);
                bF.setBackgroundColor(Color.TRANSPARENT);
                bB.setBackgroundColor(Color.TRANSPARENT);
                bC.setBackgroundColor(Color.TRANSPARENT);
                bD.setBackgroundColor(Color.TRANSPARENT);
                bA.setBackgroundColor(Color.TRANSPARENT);
                break;


        }
    }
}
