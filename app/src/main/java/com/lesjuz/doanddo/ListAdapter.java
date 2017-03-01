package com.lesjuz.doanddo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lesjuz on 2/28/2017.
 */

public class ListAdapter extends ArrayAdapter<Todo> {

    private final ArrayList<Todo> aTodo;
    private Context context;
    private List<Integer> colors;

    TextView txtItem;

    View side;


    public ListAdapter(Context context, ArrayList<Todo> aTodo) {
        super(context, R.layout.list_item);
        // TODO Auto-generated constructor stub
        this.aTodo = aTodo;
        this.context = context;
        colors = new ArrayList<Integer>();
        colors.add(Color.parseColor("#2d3e50"));
        colors.add(Color.parseColor("#2986d3"));
        colors.add(Color.parseColor("#e7473b"));
        colors.add(Color.parseColor("#2dcc70"));
        colors.add(Color.parseColor("#d40152"));
        colors.add(Color.parseColor("#20793d"));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowVirw = inflater.inflate(R.layout.list_item, parent,
                false);

        txtItem = (TextView) rowVirw.findViewById(R.id.title);
        side=rowVirw.findViewById(R.id.side);
        txtItem.setText(aTodo.get(position).getItem());
        int r = (int)(Math.random()*6);
        side.setBackgroundColor(colors.get(r));

        return rowVirw;
    }



    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.aTodo.size();
    }

    @Override
    public Todo getItem(int position) {
        // TODO Auto-generated method stub
        return this.aTodo.get(position);
    }

    @Override
    public int getPosition(Todo item) {
        // TODO Auto-generated method stub
        return super.getPosition(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}

