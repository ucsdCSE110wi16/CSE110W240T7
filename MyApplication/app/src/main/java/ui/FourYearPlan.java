package ui;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.fasterxml.jackson.databind.deser.Deserializers;

import androidstudio.edbud.com.myapplication.R;
import login.Login;

/**
 * Created by LunaLu on 2/19/16.
 */
public class FourYearPlan extends BaseActivity {

    FourYearPlanAdapter myAdapter;
    FourYearPlanListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4year);
        super.onCreateNavigation();

        listView = (FourYearPlanListView) findViewById(R.id.lsTerms);



        myAdapter = new FourYearPlanAdapter(this, BaseActivity.initialize.getTerms(), BaseActivity.initialize.getMy4YearPlan());
        listView.setDragOnLongPress(true);
        // setting list adapter
        listView.setAdapter(myAdapter);


    }
}
