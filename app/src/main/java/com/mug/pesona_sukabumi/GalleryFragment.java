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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GalleryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GalleryFragment extends Fragment {
    View v;
    private RecyclerView myRecyclerView;
    private List<GalleryActivityModel> listGallery;

    private RecyclerViewAdapterG recyclerViewAdapterG;
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

    public GalleryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GalleryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
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
        v = inflater.inflate(R.layout.fragment_gallery, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.gallery_recyclerView);
        listGallery = new ArrayList<>();
        recyclerViewAdapterG = new RecyclerViewAdapterG(getContext(),listGallery);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerViewAdapterG);

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

        database.child("penunjang")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Inisialisasi ArrayList
                        listGallery = new ArrayList<>();


                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                            GalleryActivityModel postingan = snapshot.getValue(GalleryActivityModel.class);

                            //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                            //mahasiswa.setKey(snapshot.getKey());
                            Log.d("The name", snapshot.getKey());
                            listGallery.add(postingan);
                        }

                        //Inisialisasi Adapter dan data Mahasiswa dalam bentuk Array
                        recyclerViewAdapterG = new RecyclerViewAdapterG(getContext(), listGallery);

                        //Memasang Adapter pada RecyclerView
                        myRecyclerView.setAdapter(recyclerViewAdapterG);

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
