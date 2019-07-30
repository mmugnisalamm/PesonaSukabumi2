package com.mug.pesona_sukabumi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mug.pesona_sukabumi.R;

import java.util.ArrayList;

import java.lang.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TempatPariwisataFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TempatPariwisataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TempatPariwisataFragment extends Fragment {
    View v;
    private RecyclerView myRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<TempatPariwisataModel> dailyList;
    private DatabaseReference database;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TempatPariwisataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TempatPariwisataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TempatPariwisataFragment newInstance(String param1, String param2) {
        TempatPariwisataFragment fragment = new TempatPariwisataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_tempat_pariwisata, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.daily_recyclerView);
        dailyList = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), dailyList);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerViewAdapter);

        final String current = FirebaseAuth.getInstance().getUid();

        firebaseFirestore = FirebaseFirestore.getInstance();

        /**
         * Inisialisasi dan mengambil Firebase Database Reference
         */
        database = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();
        //MyRecyclerView();

        GetData();

        return v;
    }

    //Berisi baris kode untuk mengambil data dari Database dan menampilkannya kedalam Adapter
    private void GetData(){
        Toast.makeText(getActivity(),"Mohon Tunggu Sebentar...", Toast.LENGTH_LONG).show();
        //Mendapatkan Referensi Database
        database = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference database2 = FirebaseDatabase.getInstance().getReference().child("postingan").child("User").child(String.valueOf(getId()));
        FirebaseAuth current = FirebaseAuth.getInstance();

        database.child("users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Inisialisasi ArrayList
                        dailyList = new ArrayList<>();


                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                            TempatPariwisataModel postingan = snapshot.getValue(TempatPariwisataModel.class);

                            //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                            //mahasiswa.setKey(snapshot.getKey());
                            Log.d("The name", snapshot.getKey());
                            dailyList.add(postingan);
                        }

                        //Inisialisasi Adapter dan data Mahasiswa dalam bentuk Array
                        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), dailyList);

                        //Memasang Adapter pada RecyclerView
                        myRecyclerView.setAdapter(recyclerViewAdapter);

                        //Toast.makeText(getActivity().getBaseContext(),"Data Berhasil Dimuat", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
              /*
                Kode ini akan dijalankan ketika ada error dan
                pengambilan data error tersebut lalu memprint error nya
                ke LogCat
               */
                        Toast.makeText(getActivity().getBaseContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                        Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                    }
                });
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
