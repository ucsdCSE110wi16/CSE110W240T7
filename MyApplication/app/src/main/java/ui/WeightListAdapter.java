package ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidstudio.edbud.com.myapplication.R;

/**
 * Created by LunaLu on 2/5/16.
 */
public class WeightListAdapter extends ArrayAdapter<String>{
    Context context;
    ArrayList weight;
    ArrayList percent;

    WeightListAdapter(Context c, ArrayList w, ArrayList p){

        super(c, R.layout.add_weight_row,R.id.weight,w);


        this.context = c;
        this.weight = w;
        this.percent = p;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.add_weight_row, parent, false);
        TextView weightRow = (TextView) row.findViewById(R.id.weight);
        TextView percentRow = (TextView) row.findViewById(R.id.percent);

        weightRow.setText(weight.get(position).toString());
        percentRow.setText(percent.get(position).toString());

        return row;


    }

}
