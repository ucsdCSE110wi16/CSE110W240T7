package androidstudio.edbud.com.myapplication;

import android.os.Bundle;
import android.support.design.widget.NavigationView;

import ui.BaseActivity;

public class Homepage extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);
        super.onCreateNavigation();
    }

}

