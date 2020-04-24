package com.openclassrooms.realestatemanager.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.RealEstateModel;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philippe on 28/02/2018.
 */

public class RealEstateAdapter extends RecyclerView.Adapter<RealEstateAdapter.RealEstateHolder> {

    private List<RealEstateModel> estateModels = new ArrayList<>();
    private OnItemClickListener listener;

    private int selected_position = -1;
    private boolean iSModeTabletLandActive;

    public RealEstateAdapter() {
    }

    public RealEstateAdapter(boolean iSModeTabletLandActive) {
        this.iSModeTabletLandActive = iSModeTabletLandActive;
    }

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
        holder.imageView.setImageBitmap(Utils.loadImageBitmap(currentNote.getPhotos().get(0).getImageUrl()));

        if (currentNote.getDateOfSale() != null && !currentNote.getDateOfSale().equals("")) {
            holder.avatar_off.setVisibility(View.VISIBLE);
            holder.avatar_on.setVisibility(View.GONE);
        } else {
            holder.avatar_off.setVisibility(View.GONE);
            holder.avatar_on.setVisibility(View.VISIBLE);
        }
        if (iSModeTabletLandActive) {
            int backgroundColor = (position == selected_position) ? R.color.blue_600 : R.color.whiteTextColor;
            holder.itemRealEstate.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), backgroundColor));
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
        private LinearLayout itemRealEstate;


        public RealEstateHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.estat_profil_photo);
            textViewType = itemView.findViewById(R.id.realEstate_type);
            textViewCity = itemView.findViewById(R.id.realEstate_city);
            textViewPrice = itemView.findViewById(R.id.realEstate_price);
            avatar_on = itemView.findViewById(R.id.avatar_on);
            avatar_off = itemView.findViewById(R.id.avatar_off);

            itemRealEstate = itemView.findViewById(R.id.itemRealEstate);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(estateModels.get(position));

                    if (iSModeTabletLandActive) {
                        selected_position = position;
                        notifyDataSetChanged();
                    }
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

    public void updateAdapter(int position) {

        selected_position = position;
        notifyDataSetChanged();
    }


}