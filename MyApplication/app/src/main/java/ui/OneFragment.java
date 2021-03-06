package ui;

/**
 * Created by LunaLu on 2/26/16.
 */

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
import android.support.v4.app.Fragment;
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
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import androidstudio.edbud.com.myapplication.R;
import model.Courses;
import model.Term;


public class OneFragment extends Fragment{
    TextView txAddTerm, txAddCourse;
    public static FourYearPlanAdapter oneAdapter;
    ImageButton fab;
    View fabAction1, fabAction2;
    boolean expanded = false;
    float offset1,offset2;
    Context context;
    FourYearPlan fourYearPlan;

    public OneFragment() {
        // Required empty public constructor
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setFourYearPlanView(FourYearPlan fouryearPlan){
        this.fourYearPlan = fouryearPlan;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_future, container, false);
        ExpandableListView listView = (ExpandableListView) v.findViewById(R.id.lsTerms_future);
        ArrayList<String> termList = BaseActivity.initialize.getFutureTerms();
        LinkedHashMap<String, Term> futureTermList = BaseActivity.initialize.getMyFutureTerms();
        oneAdapter = new FourYearPlanAdapter(context, termList, futureTermList, true);
        //listView.setDragOnLongPress(true);
        // setting list adapter
        listView.setAdapter(oneAdapter);

        txAddCourse = (TextView) v.findViewById(R.id.txAddCourse);
        txAddTerm = (TextView) v.findViewById(R.id.txAddTerm);
        txAddCourse.setVisibility(View.GONE);
        txAddTerm.setVisibility(View.GONE);


        final ViewGroup fabContainer = (ViewGroup) v.findViewById(R.id.fab_container);
        fab = (ImageButton) v.findViewById(R.id.fab);
        fabAction1 = v.findViewById(R.id.fab_action_1);
        fabAction2 = v.findViewById(R.id.fab_action_2);
        fabAction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddNewTerm.class));
                collapseFab();
                expanded = !expanded;
            }
        });

        fabAction2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fourYearPlan.showPop(true);
                collapseFab();
                expanded = !expanded;
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
                //System.out.println("fab, x: " + fab.getX() + " y: " + fab.getY());
                //System.out.println("fabAction1, x: " + fabAction1.getX() + " y: " + fabAction1.getY());
                //System.out.println("fabAction2, x: " + fabAction2.getX() + " y: " + fabAction2.getY());

                offset1 = fab.getX() - fabAction1.getX();
                fabAction1.setTranslationX(offset1);
                offset2 = fab.getY() - fabAction2.getY();
                fabAction2.setTranslationY(offset2);

                //System.out.println("fab, x: " + fab.getX() + " y: " + fab.getY());
                //System.out.println("fabAction1, x: " + fabAction1.getX() + " y: " + fabAction1.getY());
                //System.out.println("fabAction2, x: " + fabAction2.getX() + " y: " + fabAction2.getY());


                //System.out.println("offset1: " + offset1 + " offset2: " + offset2);
                return true;
            }
        });

        return v;
    }



    private void collapseFab() {
        fab.setImageResource(R.drawable.animated_minus);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(createCollapseAnimatorX(fabAction1, offset1),
                createCollapseAnimatorY(fabAction2, offset2),createCollapseAnimatorY(txAddCourse, offset2), createCollapseAnimatorX(txAddTerm, offset1));
        animatorSet.start();
        animateFab();
        txAddTerm.setVisibility(View.GONE);
        txAddCourse.setVisibility(View.GONE);
    }

    private void expandFab() {
        fab.setImageResource(R.drawable.animated_plus);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(createExpandAnimatorX(fabAction1, offset1),
                createExpandAnimatorY(fabAction2, offset2),createExpandAnimatorY(txAddCourse, offset2), createExpandAnimatorX(txAddTerm, offset1) );
        animatorSet.start();
        animateFab();
        txAddTerm.setVisibility(View.VISIBLE);
        txAddCourse.setVisibility(View.VISIBLE);

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

}
