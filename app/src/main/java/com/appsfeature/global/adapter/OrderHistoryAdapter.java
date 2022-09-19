package com.appsfeature.global.adapter;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.global.R;
import com.appsfeature.global.adapter.holder.AppBaseViewHolder;
import com.appsfeature.global.model.CartModel;
import com.appsfeature.global.model.ContentModel;
import com.appsfeature.global.model.ProductDetail;
import com.dynamic.DynamicModule;
import com.helper.callback.Response;
import com.helper.model.common.BaseTimeViewHolder;
import com.helper.util.BaseUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Response.OnListClickListener<CartModel> clickListener;
    private final List<CartModel> mList;
    private final String imageUrl;

    public OrderHistoryAdapter(Context context, List<CartModel> mList, Response.OnListClickListener<CartModel> clickListener) {
        this.mList = mList;
        this.clickListener = clickListener;
        this.imageUrl = DynamicModule.getInstance().getImageBaseUrl(context);
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slot_item_orders, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int i) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        myViewHolder.setData(mList.get(i), imageUrl);
    }

    private class ViewHolder extends AppBaseViewHolder implements View.OnClickListener {

        public final TextView tvTitle, tvReceipt, tvPrice, tvQuantity;
        public final ImageView ivIcon;

        private ViewHolder(View view) {
            super(view);
            ivIcon = view.findViewById(R.id.iv_icon);
            tvTitle = view.findViewById(R.id.tv_title);
            tvReceipt = view.findViewById(R.id.tv_receipt);
            tvPrice = view.findViewById(R.id.tv_price);
            tvQuantity = view.findViewById(R.id.tv_quantity);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            if(v.getId() == R.id.iv_delete){
//                clickListener.onDeleteClicked(v, getAbsoluteAdapterPosition(), mList.get(getAbsoluteAdapterPosition()));
//            }else {
//            }
            clickListener.onItemClicked(v, mList.get(getAbsoluteAdapterPosition()));
        }

        public void setData(CartModel item, String imageUrl) {
            if(item.getProducts() != null && item.getProducts().size() > 0){
                tvReceipt.setText("Receipt : " + item.getReceipt());
                tvPrice.setText("MRP :  ₹" + item.getTotal());

                ContentModel productDetail = item.getProducts().get(0);
                if (productDetail != null) {
                    if (ivIcon != null) {
                        String imagePath = getImageUrlFromJson(imageUrl, productDetail.getImage());
                        int placeHolder = R.drawable.ic_placeholder_icon;
                        if (BaseUtil.isValidUrl(imagePath)) {
                            Picasso.get().load(imagePath)
                                    .placeholder(placeHolder)
                                    .into(ivIcon);
                        } else {
                            ivIcon.setImageResource(placeHolder);
                        }
                    }
                }
                tvTitle.setText(BaseUtil.fromHtml(getTitle(item.getProducts())));
            }
        }

        private String getTitle(List<ContentModel> products) {
            List<String> names = new ArrayList<>();
            for (ContentModel item : products) {
                names.add(item.getTitle());
            }
            return TextUtils.join(", ", names);
        }
    }

}
