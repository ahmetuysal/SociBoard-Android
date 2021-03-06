package com.prolandfarming.genericlogin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.plus.PlusOneButton;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class FlightsFragment extends Fragment {


    //private OnFragmentInteractionListener mListener;
    private FloatingActionButton mAddFlightButton;
    private RecyclerView mRecyler;
    final GroupAdapter adapter = new GroupAdapter<ViewHolder>();

    public FlightsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flights, container, false);
        //Find the + button
        mAddFlightButton =  view.findViewById(R.id.flights_add_flight);
        mAddFlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FlightChatroomsActivity.class);
                //intent.putExtra(getString(R.string.INTENT_PARAM_KEY_USER), user); TODO
                startActivity(intent);
            }
        });
        mRecyler = view.findViewById(R.id.flights_recyler);
        adapter.add(new FlightItem());
        adapter.add(new FlightItem());
        adapter.add(new FlightItem());
        mRecyler.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/

}

class FlightItem extends Item<ViewHolder>{

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {

    }

    @Override
    public int getLayout() {
        return R.layout.flights_row;
    }
}
