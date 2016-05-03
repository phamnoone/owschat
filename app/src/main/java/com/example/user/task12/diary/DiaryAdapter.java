package com.example.user.task12.diary;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.task12.R;

import java.util.List;

/**
 * Created by USER on 5/24/2015.
 */
public class DiaryAdapter extends ArrayAdapter<Diar> {
    private Activity myContext;
    private List<Diar> datas;

    public DiaryAdapter(Context context, int textViewResourceId,
                           List<Diar> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        myContext = (Activity) context;
        datas = objects;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = myContext.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.diar, null);
        ImageView thumbImageView = (ImageView) rowView
                .findViewById(R.id.postThumb);

        TextView txtDiaryFrom = (TextView) rowView
                .findViewById(R.id.txtDiaryFrom);
        txtDiaryFrom.setText(datas.get(position).getFrom());

        TextView txtDiar = (TextView) rowView
                .findViewById(R.id.txtDiary);
        txtDiar.setText(datas.get(position).getDiar());
        TextView txtDiaryDate=(TextView)rowView.findViewById(R.id.txtDiaryDate);
        txtDiaryDate.setText(datas.get(position).getDate());


        return rowView;
    }
}
