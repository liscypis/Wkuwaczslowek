package com.lisowski.wojtek.wkuwaczswek.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lisowski.wojtek.wkuwaczswek.R;
import com.lisowski.wojtek.wkuwaczswek.entities.Section;

import java.util.ArrayList;

public class SelectSectionAdapter extends ArrayAdapter {
    private static final String TAG = "SectionAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private ArrayList<Section> arrayList;
    RadioButton selected = null;


    public SelectSectionAdapter(@NonNull Context context, int resource, ArrayList<Section> arrayList) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            Log.d(TAG, "getView: called with null convertview");
            convertView = layoutInflater.inflate(layoutResource, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Section currentApp = arrayList.get(position);

        viewHolder.sectionCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer pos = (Integer) viewHolder.sectionCheck.getTag();

                for (Section section : arrayList) {
                    section.setSelected(false);
                }
                arrayList.get(pos).setSelected(true);
                notifyDataSetChanged();
            }

        });

        viewHolder.sectionCheck.setTag(position);
        viewHolder.sectionTv.setText(currentApp.toString());
        viewHolder.sectionCheck.setChecked(currentApp.isSelected());

        return convertView;
    }

    private class ViewHolder {
        final TextView sectionTv;
        final RadioButton sectionCheck;

        ViewHolder(View v) {
            this.sectionTv = (TextView) v.findViewById(R.id.sectionTv);
            this.sectionCheck = (RadioButton) v.findViewById(R.id.sectionCheck);
        }

    }
}
