package com.openclassrooms.realestatemanager.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.RealEstateModel;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Philippe on 28/02/2018.
 */

/*
public class RealEstateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.realEstate_type)
    TextView textViewType;
    @BindView(R.id.estat_profil_photo)
    ImageView imageView;
    @BindView(R.id.realEstate_city)
    TextView textViewCity;
    @BindView(R.id.realEstate_price)
    TextView textViewPrice;

    // FOR DATA
    private WeakReference<RealEstateAdapter.Listener> callbackWeakRef;

    public RealEstateViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithItem(RealEstateModel item, RealEstateAdapter.Listener callback) {
        this.callbackWeakRef = new WeakReference<>(callback);
        this.textViewType.setText(item.getType());
        this.textViewCity.setText(item.getAddress());
        this.textViewPrice.setText(item.getPrice());
        Glide.with(this.imageView).load(item.getPhotos().get(0)).apply(RequestOptions.circleCropTransform()).into(this.imageView);
    }

    @Override
    public void onClick(View view) {
        RealEstateAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickDeleteButton(getAdapterPosition());
    }
}*/
