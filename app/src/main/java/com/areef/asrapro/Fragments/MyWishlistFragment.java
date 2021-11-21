package com.areef.asrapro.Fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.areef.asrapro.AdapterClasses.WishlistAdapter;
import com.areef.asrapro.DBqueries;
import com.areef.asrapro.MainActivity;
import com.areef.asrapro.ModelClasses.CartItemModel;
import com.areef.asrapro.ProductDetailsActivity;
import com.areef.asrapro.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyWishlistFragment extends Fragment {


    public MyWishlistFragment() {
        // Required empty public constructor
    }

    private RecyclerView wishlistRecyclerView;
    private Dialog loadingDialog;
    public static WishlistAdapter wishlistAdapter;

    private ImageView wishlistEmpty;
    private TextView tvWishlistEmpty, tvAddItemsToWishlist;
    private Button addNowBtn;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wishlist, container, false);

        ///loading dialog
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        ///loading dialog

        wishlistRecyclerView = view.findViewById(R.id.my_wishlist_recyclerview);

        wishlistEmpty = view.findViewById(R.id.iv_wishlist_empty);
        tvWishlistEmpty = view.findViewById(R.id.tv_wishlist_empty_msg);
        tvAddItemsToWishlist = view.findViewById(R.id.tv_add_items_to_wishlist_msg);
        addNowBtn = view.findViewById(R.id.add_now_btn);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlistRecyclerView.setLayoutManager(layoutManager);

        wishlistAdapter = new WishlistAdapter(DBqueries.wishlistModelList, true);
        wishlistRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();

        if (DBqueries.wishList.size() != 0) {
            wishlistEmpty.setVisibility(View.GONE);
            tvWishlistEmpty.setVisibility(View.GONE);
            tvAddItemsToWishlist.setVisibility(View.GONE);
            addNowBtn.setVisibility(View.GONE);
        } else {
            wishlistEmpty.setVisibility(View.VISIBLE);
            tvWishlistEmpty.setVisibility(View.VISIBLE);
            tvAddItemsToWishlist.setVisibility(View.VISIBLE);
            addNowBtn.setVisibility(View.VISIBLE);
        }

        addNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        wishlistAdapter.notifyDataSetChanged();

        if (DBqueries.wishlistModelList.size() == 0) {
            DBqueries.wishList.clear();
            DBqueries.loadWishList(getContext(), loadingDialog, true);
        } else {
            loadingDialog.dismiss();
        }
    }

}
