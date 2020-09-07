package com.example.andy.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterForKeySearch extends BaseAdapter {

    // Declare Variables
    Context context;
    String SO_ID;

    ArrayList<HashMap<String, String>> itemListContent = new ArrayList<HashMap<String, String>>();
    public AdapterForKeySearch(Context context, ArrayList<HashMap<String, String>> arraylistContent) {
        this.context = context;
        itemListContent = arraylistContent;
    }

    @Override
    public int getCount() {
        return itemListContent.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
//		return position;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.key_search_row, null);
        TextView job_name = (TextView)view.findViewById(R.id.job_name);
        TextView company_name = (TextView)view.findViewById(R.id.company_name);
        TextView vacancy_no = (TextView)view.findViewById(R.id.vacancy_no);
        TextView location = (TextView)view.findViewById(R.id.location);
        TextView education = (TextView)view.findViewById(R.id.education);


     HashMap<String, String> mapContent = new HashMap<String, String>();
        mapContent = itemListContent.get(position);
        job_name.setText( mapContent.get("job_name"));
        company_name.setText( mapContent.get("company_name"));
        location.setText( mapContent.get("job_location"));
        education.setText( mapContent.get("educational_requirement"));
        vacancy_no.setText( mapContent.get("vacancy_no"));

        return view;
    }


}
