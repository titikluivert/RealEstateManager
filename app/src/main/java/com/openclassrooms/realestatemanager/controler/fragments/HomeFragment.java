package com.openclassrooms.realestatemanager.controler.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.api.RealEstateHelper;
import com.openclassrooms.realestatemanager.model.RealEstateModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static com.openclassrooms.realestatemanager.utils.mainUtils.REAL_ESTATE;

public class HomeFragment extends Fragment {

    // 1 - Declare the SwipeRefreshLayout
    private SwipeRefreshLayout swipeRefreshLayout;
    private HomefragmentCallback callback;

    @BindView(R.id.recyclerViewHome)
    RecyclerView recyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //this.swipeRefreshLayout = findViewById(R.id.main_swipe_container);
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        this.firebaseUserSearch();
        return view;
    }

    @OnClick (R.id.addNewRealEstate_fab)
    public void addNewRealEstate(){
        if(callback!= null)
        callback.newRealEstateAdd();

        this.replaceFragment(new AddNewEstateFragment());
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

        Query firebaseSearchQuery = RealEstateHelper.getRealEstateCollection().child(REAL_ESTATE).orderByChild("userName");
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
            Glide.with(ctx).load(model.getPhotos()).apply(RequestOptions.circleCropTransform()).into(realEstatePhotoProfil);

        }
    }

    private void replaceFragment(Fragment someFragment) {

        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public interface HomefragmentCallback{

        void newRealEstateAdd();
    }
}

