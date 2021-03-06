package com.openclassrooms.realestatemanager.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.UploadImage;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.List;

public class PhotoViewAdapter extends RecyclerView.Adapter<PhotoViewAdapter.PhotoViewHolder> {
    private Context context;
    private List<UploadImage> items;
    private OnItemLongClickListener listener;
    private OnItemClickListener ClickListener;


    public PhotoViewAdapter(Context context, List<UploadImage> items) {
        this.context = context;
        this.items = items;
    }

    public UploadImage getImageAt(int position) {
        return items.get(position);
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotoViewHolder(LayoutInflater.from(context).inflate(R.layout.photos_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        holder.itemTitle.setText(items.get(position).getName());
        holder.itemImage.setImageBitmap(Utils.loadImageBitmap(items.get(position).getImageUrl()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemImage;
        private TextView itemTitle;

        public PhotoViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.item_image);
            itemTitle = view.findViewById(R.id.item_text);

            view.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemLongClicked(items.get(position));
                }
                return false;
            });

            view.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (ClickListener != null && position != RecyclerView.NO_POSITION) {
                    ClickListener.onItemClick(items.get(position));
                }
            });
        }
    }


    public interface OnItemLongClickListener {
        void onItemLongClicked(UploadImage photos);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.listener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(UploadImage photos);
    }

    public void setOnItemClickListener(OnItemClickListener ClickListener) {
        this.ClickListener = ClickListener;
    }
}


