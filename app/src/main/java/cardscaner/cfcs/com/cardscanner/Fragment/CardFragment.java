package cardscaner.cfcs.com.cardscanner.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cardscaner.cfcs.com.cardscanner.Adapter.CardListAdapter;
import cardscaner.cfcs.com.cardscanner.Model.CardListModel;
import cardscaner.cfcs.com.cardscanner.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public RecyclerView cardRecycler;
    public ArrayList<CardListModel> list = new ArrayList<>();
    public CardListAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public CardFragment() {
        // Required empty public constructor
    }

    public static CardFragment newInstance(String param1, String param2) {
        CardFragment fragment = new CardFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_card, container, false);

        cardRecycler = (RecyclerView)rootView.findViewById(R.id.card_recyceler);

        adapter = new CardListAdapter(getActivity(),list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        cardRecycler.setLayoutManager(mLayoutManager);
        cardRecycler.setItemAnimator(new DefaultItemAnimator());
        cardRecycler.setAdapter(adapter);

        prepareMemberData();

        return rootView;
    }

   private void prepareMemberData() {
        CardListModel model = new CardListModel("Himanshu Dubey","Nmtronics Privat Limited");
        list.add(model);

        model = new CardListModel("Rahul","Cfcs");
        list.add(model);

        model = new CardListModel("Jaiswal Ji","Cfcs");
        list.add(model);

         adapter.notifyDataSetChanged();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
