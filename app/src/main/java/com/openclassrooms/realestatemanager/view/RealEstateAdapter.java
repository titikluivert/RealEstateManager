package com.openclassrooms.realestatemanager.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.RealEstateModel;
import com.openclassrooms.realestatemanager.utils.mainUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philippe on 28/02/2018.
 */

public class RealEstateAdapter extends RecyclerView.Adapter<RealEstateAdapter.RealEstateHolder> {

    private List<RealEstateModel> estateModels = new ArrayList<>();
    private OnItemClickListener listener;


    @NonNull
    @Override
    public RealEstateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.properties_items, parent, false);
        return new RealEstateHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RealEstateHolder holder, int position) {
        RealEstateModel currentNote = estateModels.get(position);
        holder.textViewType.setText(currentNote.getType());
        String[] temp = currentNote.getAddress().split(",");
        holder.textViewCity.setText(temp[1].trim());
        holder.textViewPrice.setText(String.format("$%s", currentNote.getPrice()));
        holder.imageView.setImageBitmap(mainUtils.loadImageBitmap(holder.itemView.getContext(),
                currentNote.getPhotos().get(0)));
       // Glide.with(holder.itemView.getContext()).asBitmap().load().into(holder.imageView);
        if (currentNote.getDateOfSale() != null && !currentNote.getDateOfSale().equals("")) {
            holder.avatar_off.setVisibility(View.VISIBLE);
            holder.avatar_on.setVisibility(View.GONE);
        }else{
            holder.avatar_off.setVisibility(View.GONE);
            holder.avatar_on.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return estateModels.size();
    }

    public void setNotes(List<RealEstateModel> notes) {
        this.estateModels = notes;
        notifyDataSetChanged();
    }

    public RealEstateModel getRealEstateAt(int position) {
        return estateModels.get(position);
    }

    class RealEstateHolder extends RecyclerView.ViewHolder {


        private TextView textViewType;
        private ImageView imageView;
        private TextView textViewCity;
        private TextView textViewPrice;
        private View avatar_on;
        private View avatar_off;


        public RealEstateHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.estat_profil_photo);
            textViewType = itemView.findViewById(R.id.realEstate_type);
            textViewCity = itemView.findViewById(R.id.realEstate_city);
            textViewPrice = itemView.findViewById(R.id.realEstate_price);
            avatar_on = itemView.findViewById(R.id.avatar_on);
            avatar_off = itemView.findViewById(R.id.avatar_off);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(estateModels.get(position));
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(RealEstateModel note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}