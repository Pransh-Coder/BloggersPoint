package com.customer.bloggerspoint.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.customer.bloggerspoint.R;
import com.customer.bloggerspoint.pojo.BlogsPojo;

import java.util.ArrayList;

public class RecyclerAdapterBlog extends RecyclerView.Adapter<RecyclerAdapterBlog.ViewHolder> {

    Context context;
    ArrayList<BlogsPojo> blogsPojosList = new ArrayList<>();
    private ArrayList<BlogsPojo> dataFull;
    Activity activity;

    public RecyclerAdapterBlog(Context context, ArrayList<BlogsPojo> blogsPojosList, Activity activity) {
        this.context = context;
        this.blogsPojosList = blogsPojosList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blogs_list, parent, false);
        return new RecyclerAdapterBlog.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(blogsPojosList.get(position).getTitle());
        holder.description.setText(blogsPojosList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return blogsPojosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,description;
        ImageView delete,edit;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
