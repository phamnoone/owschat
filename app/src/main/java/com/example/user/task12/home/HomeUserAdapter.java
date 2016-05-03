package com.example.user.task12.home;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.task12.R;
import com.example.user.task12.users.Users;

import java.util.List;

/**
 * Created by USER on 6/16/2015.
 */
public class HomeUserAdapter extends ArrayAdapter<Users>{
    private Activity myContext;
    private List<Users> datas;

    public HomeUserAdapter(Context context, int textViewResourceId,
                        List<Users> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        myContext = (Activity) context;
        datas = objects;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = myContext.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.user_home, null);
        ImageView thumbImageView = (ImageView) rowView
                .findViewById(R.id.postThumb);

        TextView txtid= (TextView) rowView
                .findViewById(R.id.id);
        txtid.setText(datas.get(position).getName());


        return rowView;
    }
}
