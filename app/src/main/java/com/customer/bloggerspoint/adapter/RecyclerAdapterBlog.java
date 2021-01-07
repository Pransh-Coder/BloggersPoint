package com.customer.bloggerspoint.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.customer.bloggerspoint.R;
import com.customer.bloggerspoint.pojo.BlogsPojo;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterBlog extends RecyclerView.Adapter<RecyclerAdapterBlog.ViewHolder>  implements Filterable {

    Context context;
    ArrayList<BlogsPojo> blogsPojosList = new ArrayList<>();
    private ArrayList<BlogsPojo> backupList;
    Activity activity;

    public RecyclerAdapterBlog(Context context, ArrayList<BlogsPojo> blogsPojosList, Activity activity) {
        this.context = context;
        this.blogsPojosList = blogsPojosList;
        backupList = new ArrayList<>(blogsPojosList);
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

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title",blogsPojosList.get(position).getTitle());
                bundle.putString("description",blogsPojosList.get(position).getDescription());
                bundle.putString("name",blogsPojosList.get(position).getName());
                Navigation.findNavController(view).navigate(R.id.action_dashboardFragment_to_blogsDetailFragment,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return blogsPojosList.size();
    }

    @Override
    public Filter getFilter() {
        return blogsFilter;
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

    private Filter blogsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<BlogsPojo>filteredData = new ArrayList<>();
            if (charSequence==null||charSequence.length()==0){
                filteredData.addAll(backupList);
            }
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (BlogsPojo item : backupList)
                {
                    if (item.getTitle().toLowerCase().startsWith(filterPattern))
                    {
                        filteredData.add(item);

                    }
                    else if (item.getDescription().toLowerCase().contains(filterPattern))
                    {
                        filteredData.add(item);
                    }
                    else {

                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredData;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            blogsPojosList.clear();
            blogsPojosList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
