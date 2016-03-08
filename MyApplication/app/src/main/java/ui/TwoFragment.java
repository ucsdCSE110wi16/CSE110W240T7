package ui;

/**
 * Created by LunaLu on 2/26/16.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import androidstudio.edbud.com.myapplication.R;
import model.Term;


public class TwoFragment extends Fragment{
    public static FourYearPlanAdapter twoAdapter;
    ExpandableListView listView;
    Context context;

    public TwoFragment() {
        // Required empty public constructor
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_past, container, false);
        listView = (ExpandableListView) v.findViewById(R.id.lsTerms_past);
        ArrayList<String> termList = BaseActivity.initialize.getPastTerms();
        LinkedHashMap<String, Term> pastTermList = BaseActivity.initialize.getMyPastTerms();
        twoAdapter = new FourYearPlanAdapter(context, termList, pastTermList);
        //listView.setDragOnLongPress(true);
        // setting list adapter
        listView.setAdapter(twoAdapter);
        return v;
        // Inflate the layout for this fragment
    }

}
