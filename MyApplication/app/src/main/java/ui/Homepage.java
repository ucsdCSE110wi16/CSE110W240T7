package ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.widget.TextView;

import androidstudio.edbud.com.myapplication.R;
import model.user;
import ui.BaseActivity;

public class Homepage extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);
        super.onCreateNavigation();
    }

    @Override
    public void onBackPressed(){
        // DO NOTHING
    }
}

