package com.example.monitoriadetartarugas.listViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.monitoriadetartarugas.R;
import com.example.monitoriadetartarugas.domain.entitys.Activities;
import com.example.monitoriadetartarugas.domain.entitys.Beach;

import java.util.ArrayList;
import java.util.List;

public class BeachAdapter extends ArrayAdapter<Beach> {
    private List<Beach> beachList = new ArrayList<>();
    private Context context;

    public BeachAdapter(List<Beach> beachList, Context context) {
        super(context, R.layout.item_layout, beachList);
        this.beachList = beachList;
        this.context = context;
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View row = layoutInflater.inflate(R.layout.item_layout_2, parent, false);

        TextView txt_header = row.findViewById(R.id.txt_header);
        TextView txt_subheader = row.findViewById(R.id.txt_subheader);

        txt_header.setText(beachList.get(position).getBeach());
        txt_subheader.setText(beachList.get(position).getIsland());

        return row;
    }
}
