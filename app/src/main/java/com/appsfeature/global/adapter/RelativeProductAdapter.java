package com.appsfeature.global.adapter;

import static com.appsfeature.global.util.SupportUtil.getImageUrlFromJson;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.global.R;
import com.appsfeature.global.model.ContentModel;
import com.helper.callback.Response;
import com.helper.util.BaseUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RelativeProductAdapter extends RecyclerView.Adapter<RelativeProductAdapter.Object> implements Filterable {
    List<ContentModel>data;
    Context context;
    private final Response.OnClickListener<ContentModel> mCallback;

    public RelativeProductAdapter(List<ContentModel> data, Context context, Response.OnClickListener<ContentModel> mCallback) {
        this.data = data;
        this.context = context;
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public Object onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Object(LayoutInflater.from(context).inflate(R.layout.relativ_product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Object holder, int position) {
        String imagePath = getImageUrlFromJson("", data.get(position).getImage());
        int placeHolder = R.drawable.ic_placeholder_icon;
        if (BaseUtil.isValidUrl(imagePath)) {
            getPicasso().load(imagePath)
                    .resize(250,512)
                    .placeholder(placeHolder)
                    .into(holder.iv_icon);
        } else {
            holder.iv_icon.setImageResource(placeHolder);
        }
        holder.tv_title.setText(data.get(position).getTitle());
        holder.tv_price.setText(getPrice(data.get(position).getPrice(), data.get(position).getDiscountPrice()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class Object extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView iv_icon;
        TextView tv_title,tv_price;

        public Object(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            iv_icon=itemView.findViewById(R.id.iv_icon);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_price=itemView.findViewById(R.id.tv_price);
        }

        @Override
        public void onClick(View v) {
            if(getAbsoluteAdapterPosition() >= 0 && getAbsoluteAdapterPosition() < data.size()) {
                mCallback.onItemClicked(v, data.get(getAbsoluteAdapterPosition()));
            }
        }
    }
    private Picasso picasso;

    public Picasso getPicasso() {
        if (picasso == null) picasso = Picasso.get();
        return picasso;
    }
    private SpannableString getPrice(int price, int discountPrice) {
        SpannableString spannable;
        if(discountPrice >= price){
            spannable = new SpannableString("MRP :  ₹" + price);
        }else {
            spannable = new SpannableString("MRP : ₹" + price + "  ₹" + discountPrice);
            spannable.setSpan(new ForegroundColorSpan(Color.GRAY), 6, ((String.valueOf(price).length()) + 9), 0);
            spannable.setSpan(new StrikethroughSpan(), 6, ((String.valueOf(price).length()) + 9), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }
}
