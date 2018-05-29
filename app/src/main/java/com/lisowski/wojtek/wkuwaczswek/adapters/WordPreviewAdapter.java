package com.lisowski.wojtek.wkuwaczswek.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lisowski.wojtek.wkuwaczswek.R;
import com.lisowski.wojtek.wkuwaczswek.Words;

import java.util.ArrayList;

public class WordPreviewAdapter extends ArrayAdapter {
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private ArrayList<Words> arrayList;
    RadioButton selected = null;


    public WordPreviewAdapter(@NonNull Context context, int resource, ArrayList<Words> arrayList) {
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
            convertView = layoutInflater.inflate(layoutResource, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Words currentApp = arrayList.get(position);
        viewHolder.wordTV.setText(currentApp.getWord());
        viewHolder.translationTV.setText(currentApp.getTranslation());

        return convertView;
    }

    private class ViewHolder {
        final TextView wordTV;
        final TextView translationTV;

        ViewHolder(View v) {
            this.wordTV = (TextView) v.findViewById(R.id.previewWordTV);
            this.translationTV = (TextView) v.findViewById(R.id.previewTranslationTV);
        }

    }
}
