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
import com.lisowski.wojtek.wkuwaczswek.entities.Words;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter {
    private static final String TAG = "SectionAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private ArrayList<Words> arrayList;
    private static RadioButton selected = null;


    public WordAdapter(@NonNull Context context, int resource, ArrayList<Words> arrayList) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
        //arrayList.get(0).setSelected(true);
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

        viewHolder.wordRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer pos = (Integer) viewHolder.wordRadioButton.getTag();
                for (Words w : arrayList) {
                    w.setSelected(false);
                }
                arrayList.get(pos).setSelected(true);
                notifyDataSetChanged();
            }
        });
        Words currentApp = arrayList.get(position);

        viewHolder.wordRadioButton.setTag(position);
        viewHolder.wordTV.setText(currentApp.getWord());
        viewHolder.translationTV.setText(currentApp.getTranslation());
        viewHolder.wordRadioButton.setChecked(currentApp.isSelected());

        return convertView;
    }

    private class ViewHolder {
        final TextView wordTV;
        final TextView translationTV;
        final RadioButton wordRadioButton;

        ViewHolder(View v) {
            this.wordTV = (TextView) v.findViewById(R.id.previewWordTV);
            this.translationTV = (TextView) v.findViewById(R.id.previewTranslationTV);
            this.wordRadioButton = (RadioButton) v.findViewById(R.id.wordRadioButton);
        }
    }
}
