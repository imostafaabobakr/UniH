package com.jdev.kolya.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jdev.kolya.R;

import java.util.ArrayList;

/**
 * Created by Java_Dude on 8/21/2018.
 */

public class SubjectsA extends RecyclerView.Adapter<SubjectsA.holder> {
    ArrayList<String> arrayList;
    Context conn;
    int color;


    public SubjectsA( ArrayList<String> arrayList,int color) {
        this.arrayList = arrayList;
        this.color = color;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        conn = parent.getContext();
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        String Text = arrayList.get(position);
        holder.textView.setText(Text);

            switch (color) {
                case 1 :
                    holder.textView.setBackgroundResource(R.drawable.cat_bg);
                    break;
                case 2 :
                    holder.textView.setBackgroundResource(R.drawable.cat_bg2);
                    break;
                case 3 :
                    holder.textView.setBackgroundResource(R.drawable.cat_bg3);
                    break;
                case 4 :
                    holder.textView.setBackgroundResource(R.drawable.cat_bg4);
                    break;
                case 5 :
                    holder.textView.setBackgroundResource(R.drawable.cat_bg5);
                    break;
                case 6 :
                    holder.textView.setBackgroundResource(R.drawable.cat_bg6);
                    break;
                case 7 :
                    holder.textView.setBackgroundResource(R.drawable.cat_bg7);
                    break;


        }



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class holder extends RecyclerView.ViewHolder
    {
        TextView textView;

        public holder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txtr);
        }
    }
}
