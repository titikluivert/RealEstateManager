package com.openclassrooms.realestatemanager.controler.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.api.RealEstateHelper;
import com.openclassrooms.realestatemanager.model.RealEstateModel;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static com.openclassrooms.realestatemanager.utils.mainUtils.REAL_ESTATE;

public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // 1 - Declare the SwipeRefreshLayout
    private SwipeRefreshLayout swipeRefreshLayout;


    @BindView(R.id.recyclerViewHome)
    RecyclerView recyclerView;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //this.swipeRefreshLayout = findViewById(R.id.main_swipe_container);
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
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


}

