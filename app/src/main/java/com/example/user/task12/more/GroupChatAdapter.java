package com.example.user.task12.more;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.task12.R;

import java.util.List;

/**
 * Created by USER on 6/4/2015.
 */
public class GroupChatAdapter extends ArrayAdapter<Group>{
    private Activity myContext;
    private List<Group> datas;

    public GroupChatAdapter(Context context, int textViewResourceId,
                        List<Group> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        myContext = (Activity) context;
        datas = objects;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = myContext.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.group, null);
        TextView idGroupChat = (TextView) rowView.findViewById(R.id.idGroupChat);
        idGroupChat.setText(datas.get(position).getId());
        TextView dateGroupChat=(TextView)rowView.findViewById(R.id.dateGroupChat);
        dateGroupChat.setText(datas.get(position).getDate());
        return rowView;
    }

}
