package cse.marmara.marunify.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cse.marmara.marunify.R;
import cse.marmara.marunify.adapters.ArtistAdapter;
import cse.marmara.marunify.adapters.SongAdapter;
import cse.marmara.marunify.model.Artist;
import cse.marmara.marunify.model.Song;

public class ArtistFragment extends Fragment {
    private View view;

    private RecyclerView recyclerView;

    private List<Artist> mList;
    private FragmentManager frMng;

    public ArtistFragment() {
        // empty constructor
    }

    @SuppressLint("ValidFragment")
    public ArtistFragment(FragmentManager frMng, List<Artist> mList) {
        this.frMng = frMng;
        this.mList = mList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.subfragment_view, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        LinearLayoutManager mLinearLayout = new LinearLayoutManager(getActivity());
        recyclerView
                .setLayoutManager(mLinearLayout);//Linear Items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLinearLayout.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);


        ArtistAdapter adapter = new ArtistAdapter(frMng, getActivity(), mList);
        recyclerView.setAdapter(adapter);// set adapter on recyclerview

        return view;

    }

}