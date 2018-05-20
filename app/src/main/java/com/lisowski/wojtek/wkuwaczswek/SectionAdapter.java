package com.lisowski.wojtek.wkuwaczswek;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SectionAdapter extends ArrayAdapter {
    private static final String TAG = "SectionAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private ArrayList<Section> arrayList;
    private Context context;

    public SectionAdapter(@NonNull Context context, int resource, ArrayList<Section> arrayList) {
        super(context, resource);
        this.context = context;
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

        Section currentApp = arrayList.get(position);

        viewHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer pos = (Integer) viewHolder.check.getTag();
                Toast.makeText(context,"Checkbox "+pos+" clicked!", Toast.LENGTH_SHORT).show();
                if (arrayList.get(pos).isSelected())
                    arrayList.get(pos).setSelected(false);
                else arrayList.get(pos).setSelected(true);
                Log.d(TAG, "onClick: chuju jestem teraz na " + arrayList.get(pos).isSelected());
            }
        });
        viewHolder.check.setTag( position);
        viewHolder.label.setText(currentApp.toString());
        viewHolder.check.setChecked(currentApp.isSelected());

        return convertView;
    }

    private class ViewHolder {
        final TextView label;
        final CheckBox check;

        ViewHolder(View v) {
            this.label = (TextView) v.findViewById(R.id.label);
            this.check = (CheckBox) v.findViewById(R.id.sectionCheck);
        }

    }
}
