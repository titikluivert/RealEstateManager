package com.openclassrooms.realestatemanager.controler.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.api.RealEstateHelper;
import com.openclassrooms.realestatemanager.model.RealEstateModel;
import com.openclassrooms.realestatemanager.model.UsersModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.openclassrooms.realestatemanager.utils.mainUtils.REAL_ESTATE;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.recyclerViewHome)
    RecyclerView recyclerView;
    // 1 - Declare the SwipeRefreshLayout
    private SwipeRefreshLayout swipeRefreshLayout;
    private HomefragmentCallback callback;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //this.swipeRefreshLayout = findViewById(R.id.main_swipe_container);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        this.firebaseUserSearch();
        return view;
    }

    @OnClick(R.id.addNewRealEstate_fab)
    public void addNewRealEstate() {
        if (callback != null)
            callback.newRealEstateAdd();

        this.replaceFragment(new LoginFragment());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (HomefragmentCallback) context;
    }

    // 2 - Configure the SwipeRefreshLayout
    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // executeHttpRequestWithRetrofit();
            }
        });
    }

    // -----------------
    // CONFIGURATION
    // -----------------
    private void firebaseUserSearch() {
        UsersModel usersModel = getUserInFireStore();
        Query firebaseSearchQuery = RealEstateHelper.getRealEstateCollection().child(REAL_ESTATE).orderByChild("type");
        FirebaseRecyclerAdapter<RealEstateModel, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RealEstateModel, UsersViewHolder>(

                RealEstateModel.class,
                R.layout.properties_items,
                UsersViewHolder.class,
                firebaseSearchQuery
        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, RealEstateModel model, int position) {

                viewHolder.setDetails(getContext(), model);
            }
        };

        this.recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public interface HomefragmentCallback {
        void newRealEstateAdd();
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        private void setDetails(Context ctx, RealEstateModel model) {


            ImageView realEstatePhotoProfil = mView.findViewById(R.id.estat_profil_photo);
            TextView realEstateType = mView.findViewById(R.id.realEstate_type);
            TextView realEstateAddress = mView.findViewById(R.id.realEstate_address);
            TextView realEstatePrice = mView.findViewById(R.id.realEstate_price);


            realEstateType.setText(model.getType());
            realEstateAddress.setText(model.getAddress());
            realEstatePrice.setText(model.getPrice());
           // Glide.with(ctx).load(model.getPhotos()).apply(RequestOptions.circleCropTransform()).into(realEstatePhotoProfil);

        }
    }
}

