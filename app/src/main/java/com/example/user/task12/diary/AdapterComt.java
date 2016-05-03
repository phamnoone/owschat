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
 * Created by USER on 5/26/2015.
 */
public class AdapterComt extends ArrayAdapter<comt>{
    private Activity myContext;
    private List<comt> datas;

    public AdapterComt(Context context, int textViewResourceId,
                        List<comt> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        myContext = (Activity) context;
        datas = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = myContext.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.comment_layout, null);
        ImageView thumbImageView = (ImageView) rowView
                .findViewById(R.id.postThumb);

        TextView cmtFrom= (TextView) rowView
                .findViewById(R.id.cmtFrom);
        cmtFrom.setText(datas.get(position).getFrom());

        TextView cmt= (TextView) rowView
                .findViewById(R.id.cmt);
        cmt.setText(datas.get(position).getCmt());
        TextView cmtDate=(TextView)rowView.findViewById(R.id.cmtDate);
        cmtDate.setText(datas.get(position).getDate());


        return rowView;
    }
}
