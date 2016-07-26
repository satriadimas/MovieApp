package com.example.satriadimaspermana.movieapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.satriadimaspermana.movieapp.sample.SampleActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Satria Dimas Permana on 7/15/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RootObjectViewHolder> {

    private List<Result> ResultList;
    public OnClickItem itemClick;
    public final static String KEY_MEMBER = "keymember";
    Context c;

    public MyAdapter(OnClickItem itemClick) {
        this.itemClick = itemClick;
        ResultList = new ArrayList<>();
    }

    public interface OnClickItem {
        public void OnClick(Result result);
    }

    public MyAdapter() {
        ResultList = new ArrayList<>();
    }
//    private void add(RootObject item) {
//        ResultList.add(item);
//        notifyItemInserted(ResultList.size() - 1);
//    }

    public void addAll(List<Result> r) {
        //  ResultList.removeAll(r);
        ResultList.addAll(r);
    }

    public void removeAll(List<Result> r) {
        ResultList.clear();
//        ResultList.removeAll(r);
    }

    public Result getItem(int position) {
        return ResultList.get(position);
    }

    @Override
    public RootObjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cardview, parent, false);
        RootObjectViewHolder rootObjectViewHolder = new RootObjectViewHolder(view);
        c = parent.getContext();
        return rootObjectViewHolder;
    }

    @Override
    public void onBindViewHolder(RootObjectViewHolder holder, int position) {
        final Result object = ResultList.get(position);
        Picasso.with(c).load("http://image.tmdb.org/t/p/w185/" + object.getPoster_path()).into(holder.ResultImage);
        Log.e("", "onBindViewHolder: "+object.backdrop_path);
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                itemClick.OnClick(RootObject);
                Log.e("", "onClick: "+object);
                Intent pindah = new Intent(c, SampleActivity.class);
                pindah.putExtra(SampleActivity.KEY_MEMBER,object);
                c.startActivity(pindah);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ResultList.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    static class RootObjectViewHolder extends RecyclerView.ViewHolder {

        ImageView ResultImage;
        View v;

        public RootObjectViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            ResultImage = (ImageView) itemView.findViewById(R.id.image);
//        }
        }
    }
}