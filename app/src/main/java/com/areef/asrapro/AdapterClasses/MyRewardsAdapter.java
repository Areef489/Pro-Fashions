package com.areef.asrapro.AdapterClasses;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.areef.asrapro.ModelClasses.CartItemModel;
import com.areef.asrapro.ModelClasses.RewardModel;
import com.areef.asrapro.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyRewardsAdapter extends RecyclerView.Adapter<MyRewardsAdapter.Viewholder> {

    private List<RewardModel> rewardModelList;
    private Boolean useMiniLayout = false;
    private RecyclerView coupensRecyclerView;
    private LinearLayout selectedCoupen;
    private String productOrginalPrice;
    private TextView selectedCoupenTitle;
    private TextView selectedCoupenExpiryDate;
    private TextView selectedCoupenBody;
    private TextView discountedPrice;
    private int cartItemPosition = -1;
    private List<CartItemModel> cartItemModelList;

    public MyRewardsAdapter(List<RewardModel> rewardModelList, Boolean useMiniLayout) {
        this.rewardModelList = rewardModelList;
        this.useMiniLayout = useMiniLayout;
    }

    public MyRewardsAdapter(List<RewardModel> rewardModelList, Boolean useMiniLayout, RecyclerView coupensRecyclerView, LinearLayout selectedCoupen, String productOrginalPrice, TextView coupenTitle, TextView coupenExpiryDate, TextView coupenBody, TextView discountedPrice) {
        this.rewardModelList = rewardModelList;
        this.useMiniLayout = useMiniLayout;
        this.coupensRecyclerView = coupensRecyclerView;
        this.selectedCoupen = selectedCoupen;
        this.productOrginalPrice = productOrginalPrice;
        this.selectedCoupenTitle = coupenTitle;
        this.selectedCoupenExpiryDate = coupenExpiryDate;
        this.selectedCoupenBody = coupenBody;
        this.discountedPrice = discountedPrice;
    }

    public MyRewardsAdapter(int cartItemPosition, List<RewardModel> rewardModelList, Boolean useMiniLayout, RecyclerView coupensRecyclerView, LinearLayout selectedCoupen, String productOrginalPrice, TextView coupenTitle, TextView coupenExpiryDate, TextView coupenBody, TextView discountedPrice,List<CartItemModel> cartItemModelList) {
        this.rewardModelList = rewardModelList;
        this.useMiniLayout = useMiniLayout;
        this.coupensRecyclerView = coupensRecyclerView;
        this.selectedCoupen = selectedCoupen;
        this.productOrginalPrice = productOrginalPrice;
        this.selectedCoupenTitle = coupenTitle;
        this.selectedCoupenExpiryDate = coupenExpiryDate;
        this.selectedCoupenBody = coupenBody;
        this.discountedPrice = discountedPrice;
        this.cartItemPosition = cartItemPosition;
        this.cartItemModelList = cartItemModelList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (useMiniLayout) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mini_rewards_item_layout, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mini_rewards_item_layout, viewGroup, false);
        }
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int position) {
        String coupenId = rewardModelList.get(position).getCoupenId();
        String type = rewardModelList.get(position).getType();
        Date validity = rewardModelList.get(position).getTimestamp();
        String body = rewardModelList.get(position).getCoupenBody();
        String lowerLimit = rewardModelList.get(position).getLowerLimit();
        String upperLimit = rewardModelList.get(position).getUpperLimit();
        String discORamt = rewardModelList.get(position).getdiscORamt();
        Boolean alreadyUsed = rewardModelList.get(position).getAlreadyUsed();

        viewholder.setDate(coupenId, type, validity, body, upperLimit, lowerLimit, discORamt,alreadyUsed);


    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView coupenTitle;
        private TextView coupenExpireDate;
        private TextView coupenBody;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            coupenTitle = itemView.findViewById(R.id.coupen_title);
            coupenExpireDate = itemView.findViewById(R.id.coupen_validity);
            coupenBody = itemView.findViewById(R.id.coupen_body);
        }

        private void setDate(final String coupenId, final String type, final Date validity, final String body, String upperLimit, String lowerLimit, String discORamt, boolean alreadyUsed) {

            if (type.equals("Discount")) {
                coupenTitle.setText(type);
            } else {
                coupenTitle.setText("FLAT Rs." + discORamt + " OFF");
            }
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM YYYY");
            if (alreadyUsed) {
                coupenExpireDate.setText("Already used");
                coupenExpireDate.setTextColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
                coupenBody.setTextColor(Color.parseColor("#50ffffff"));
                coupenTitle.setTextColor(Color.parseColor("#50ffffff"));
            } else {
                coupenBody.setTextColor(Color.parseColor("#ffffff"));
                coupenTitle.setTextColor(Color.parseColor("#ffffff"));
                coupenExpireDate.setTextColor(itemView.getContext().getResources().getColor(R.color.coupenPurple));
                coupenExpireDate.setText("till " + simpleDateFormat.format(validity));
            }
            coupenBody.setText(body);
            if (useMiniLayout) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!alreadyUsed) {
                            selectedCoupenTitle.setText(type);
                            selectedCoupenExpiryDate.setText(simpleDateFormat.format(validity));
                            selectedCoupenBody.setText(body);

                            if (Long.valueOf(productOrginalPrice) > Long.valueOf(lowerLimit) && Long.valueOf(productOrginalPrice) < Long.valueOf(upperLimit)) {

                                if (type.equals("Discount")) {
                                    Long discountedAmount = Long.valueOf(productOrginalPrice) * Long.valueOf(discORamt) / 100;
                                    discountedPrice.setText("\u20B9" + String.valueOf(Long.valueOf(productOrginalPrice) - discountedAmount));
                                } else {
                                    discountedPrice.setText("\u20B9" + String.valueOf(Long.valueOf(productOrginalPrice) - Long.valueOf(discORamt)));
                                }
                                if (cartItemPosition != -1) {
                                    cartItemModelList.get(cartItemPosition).setSelectedCoupenId(coupenId);
                                }
                            } else {
                                if (cartItemPosition != -1) {
                                    cartItemModelList.get(cartItemPosition).setSelectedCoupenId(null);
                                }
                                discountedPrice.setText("Invalid");
                                Toast.makeText(itemView.getContext(), "Sorry ! Product does not matches the coupen terms.", Toast.LENGTH_SHORT).show();
                            }

                            if (coupensRecyclerView.getVisibility() == View.GONE) {
                                coupensRecyclerView.setVisibility(View.VISIBLE);
                                selectedCoupen.setVisibility(View.GONE);
                            } else {
                                coupensRecyclerView.setVisibility(View.GONE);
                                selectedCoupen.setVisibility(View.VISIBLE);
                            }
                        }

                    }
                });
            }
        }
    }
}
