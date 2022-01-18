package com.areef.asrapro.AdapterClasses;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.areef.asrapro.CategoryActivity;
import com.areef.asrapro.ModelClasses.SubCategoryModel;
import com.areef.asrapro.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {

    private List<SubCategoryModel> subCategoryModelList;
    private int lastPosition = -1;

    public SubCategoryAdapter(List<SubCategoryModel> subCategoryModelList) {
        this.subCategoryModelList = subCategoryModelList;
    }

    @NonNull
    @Override
    public SubCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_sub_category_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.ViewHolder viewHolder, int position) {
        String icon = subCategoryModelList.get(position).getSubCategoryIconLink();
        String name = subCategoryModelList.get(position).getSubCategoryName();
        viewHolder.setSubCategory(name, position);
        viewHolder.setCategoryIcon(icon);

        if (lastPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.fade_in);
            viewHolder.itemView.setAnimation(animation);
            lastPosition = position;
        }

    }

    @Override
    public int getItemCount() {
        return subCategoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView subCategoryIcon;
        private TextView subCategoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subCategoryIcon = itemView.findViewById(R.id.sub_category_icon);
            subCategoryName = itemView.findViewById(R.id.category_name);
        }

        private void setCategoryIcon(String iconUrl) {
            if (!iconUrl.equals("null")) {
                Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.drawable.icon_placeholder)).into(subCategoryIcon);
            } else {
                subCategoryIcon.setImageResource(R.drawable.home_icon);
            }

        }

        private void setSubCategory(final String name, int position) {
            subCategoryName.setText(name);

            if (!name.equals("")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position != 0) {
                            Intent categoryIntent = new Intent(itemView.getContext(), CategoryActivity.class);
                            categoryIntent.putExtra("CategoryName", name);
                            itemView.getContext().startActivity(categoryIntent);
                        }
                    }
                });
            }
        }
    }
}
