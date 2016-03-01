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


public class OneFragment extends Fragment{
    private Context context;

    public OneFragment() {
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_future, container, false);
        ExpandableListView listView = (ExpandableListView) v.findViewById(R.id.lsTerms_future);
        ArrayList<String> termList = BaseActivity.initialize.getFutureTerms();
        LinkedHashMap<String, Term> futureTermList = BaseActivity.initialize.getMyFutureTerms();
        FourYearPlanAdapter myAdapter = new FourYearPlanAdapter(context, termList, futureTermList);
        //listView.setDragOnLongPress(true);
        // setting list adapter
        listView.setAdapter(myAdapter);

        return v;
    }

}
