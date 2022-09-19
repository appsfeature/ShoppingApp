package com.appsfeature.global.adapter.app;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.global.R;
import com.appsfeature.global.model.UserModel;
import com.helper.callback.Response;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Response.OnClickListener<UserModel> clickListener;
    private final List<UserModel> mList;

    public AddressAdapter(List<UserModel> mList, Response.OnClickListener<UserModel> clickListener) {
        this.mList = mList;
        this.clickListener = clickListener;
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slot_item_address, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int i) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        myViewHolder.tvName.setText(getValidName(mList.get(i).getTitle()));
        myViewHolder.tvSubTitle.setText(getAddress(mList.get(i)));
    }

    private StringBuilder getAddress(UserModel userModel) {
        StringBuilder address = new StringBuilder();
        if (!TextUtils.isEmpty(userModel.getAddress())) {
            address.append("Address : ");
            address.append(userModel.getAddress());
//            address.append(" (");
//            address.append(userModel.getZipcode());
//            address.append(")");
        }
        if (!TextUtils.isEmpty(userModel.getPhone())) {
            address.append("\nMobile : ");
            address.append(userModel.getPhone());
        }
        return address;
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvName, tvSubTitle;

        private ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_title);
            tvSubTitle = v.findViewById(R.id.tv_sub_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClicked(v, mList.get(getAbsoluteAdapterPosition()));
        }
    }

    private String getValidName(String name) {
        if(name != null) {
            StringBuilder sb = new StringBuilder(name);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            return sb.toString();
        }else {
            return "";
        }
    }
}
