package com.mug.pesona_sukabumi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mug.pesona_sukabumi.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.lang.*;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    FirebaseUser currentUSer;
    FirebaseAuth mAuth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    MapView mMapView;
    private GoogleMap googleMap;

    private TextView tvPickUpFrom2, tvDestLocation;
    public TextView tvPickUpAddr, tvPickUpLatLng, tvPickUpName;
    private ImageView imgPost;
    private TextView newPostDesc, latitude, longitude;
    private TextView newPostDate;
    private TextView spGunung;
    private Button btnPost;
    private Double lat1, lon1;
    private LatLng pickUpLatLng = null;
    public LatLng locationLatLng = null;
    private TextView instagram;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init
        mAuth = FirebaseAuth.getInstance();
        currentUSer = mAuth.getCurrentUser();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView btnEdit = (Button) view.findViewById(R.id.btn_edit_user);
        TextView navUsername = (TextView) view.findViewById(R.id.navName);
        TextView navEmail = (TextView) view.findViewById(R.id.emailUser);
        ImageView navImg = (ImageView) view.findViewById(R.id.navImg);
        navUsername.setText(currentUSer.getDisplayName());
        navEmail.setText(currentUSer.getEmail());
        Glide.with(this).load(currentUSer.getPhotoUrl()).into(navImg);


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), EditAkun.class);
                getActivity().startActivity(intent);
            }
        });



        return view;


    }


    private FragmentManager getSupportFragmentManager() {
        return null;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
