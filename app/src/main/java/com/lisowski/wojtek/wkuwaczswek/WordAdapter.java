package com.lisowski.wojtek.wkuwaczswek;

import android.content.Context;
import android.content.Intent;
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

public class WordAdapter extends ArrayAdapter {
    private static final String TAG = "SectionAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private ArrayList<Words> arrayList;
    private Context context;
    private int sectionID;

    public WordAdapter(@NonNull Context context, int resource, ArrayList<Words> arrayList, int sectionID) {
        super(context, resource);
        this.context = context;
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
        this.sectionID = sectionID;
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

        Words currentApp = arrayList.get(position);

        viewHolder.wordCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer pos = (Integer) viewHolder.wordCheckBox.getTag();
                Toast.makeText(context, "Checkbox " + pos + " clicked!", Toast.LENGTH_SHORT).show();
                if (arrayList.get(pos).isSelected())
                    arrayList.get(pos).setSelected(false);
                else arrayList.get(pos).setSelected(true);
                Intent intent = new Intent(context, EditWord.class);
                intent.putExtra("WORD_ID", pos);
                intent.putExtra("SECTION_ID",sectionID);

                if (intent != null)
                    context.startActivity(intent);

            }
        });
        viewHolder.wordCheckBox.setTag(position);
        viewHolder.wordTV.setText(currentApp.getWord());
        viewHolder.translationTV.setText(currentApp.getTranslation());
        viewHolder.wordCheckBox.setChecked(currentApp.isSelected());

        return convertView;
    }

    private class ViewHolder {
        final TextView wordTV;
        final TextView translationTV;
        final CheckBox wordCheckBox;

        ViewHolder(View v) {
            this.wordTV = (TextView) v.findViewById(R.id.wordTV);
            this.translationTV = (TextView) v.findViewById(R.id.translationTV);
            this.wordCheckBox = (CheckBox) v.findViewById(R.id.wordCheckBox);
        }

    }
}
