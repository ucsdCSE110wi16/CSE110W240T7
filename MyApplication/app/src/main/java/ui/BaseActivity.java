package ui;

/**
 * Created by LunaLu on 2/4/16.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import Constant.Constant;
import androidstudio.edbud.com.myapplication.R;
import login.Login;
import model.IndividualAssignment;
import model.IndividualCourse;
import model.User;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView headerCollege;
    TextView headerMajor;
    TextView headerYear;
    TextView headerName;
    public static User initialize = new User();
    public static String uid;
    protected static Toolbar toolbar;





    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        Firebase ref = new Firebase("https://edbud.firebaseio.com/userInfo/" + initialize.uid);
        ref.addValueEventListener(new secondValueEventListener());


    }

    protected void onCreateNavigation() {


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_navi);

        headerCollege = (TextView) headerView.findViewById(R.id.myCollege);
        headerMajor = (TextView) headerView.findViewById(R.id.myMajor);
        headerName = (TextView) headerView.findViewById(R.id.myName);
        //Log.v("UID",Login.initialize.UID);
        //Log.v("College",Login.initialize.college);


    }
    class secondValueEventListener implements ValueEventListener {


        public secondValueEventListener(){
            super();
        }
        @Override
        public void onDataChange(DataSnapshot snapshot) {

            headerCollege.setText(snapshot.child("college").getValue().toString());

            headerMajor.setText(snapshot.child("major").getValue().toString());

            headerName.setText(snapshot.child("fullName").getValue().toString());

            Log.v("Here", "User is created");
            if (IndividualCourse.listAdapter != null)
                IndividualCourse.listAdapter.notifyDataSetChanged();
        }
        @Override
        public void onCancelled(FirebaseError firebaseError) {
            System.out.println("The read failed: " + firebaseError.getMessage());
        }


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_homepage) {
            startActivity(new Intent(this, Homepage.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        } else if (id == R.id.nav_course) {
            startActivity(new Intent(this, CoursePage.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        } else if (id == R.id.nav_4yearplan) {
            startActivity(new Intent(this, FourYearPlan.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        }
        else if (id == R.id.nav_logout) {
            SharedPreferences sp = this.getSharedPreferences(Constant.myPrefer,Context.MODE_PRIVATE);
            sp.edit().putBoolean("AutoLogin",false).commit();
            sp.edit().putString("check", "").commit();
            startActivity(new Intent(this, Login.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}


