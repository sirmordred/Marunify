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
import cse.marmara.marunify.adapters.GenreAdapter;
import cse.marmara.marunify.adapters.SongAdapter;
import cse.marmara.marunify.model.Genre;
import cse.marmara.marunify.model.Song;

public class GenreFragment extends Fragment {
    private View view;

    private RecyclerView recyclerView;

    private List<Genre> mList;
    private FragmentManager frMng;

    public GenreFragment() {
        // empty constructor
    }

    @SuppressLint("ValidFragment")
    public GenreFragment(FragmentManager frMng, List<Genre> mList) {
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


        GenreAdapter adapter = new GenreAdapter(frMng, getActivity(), mList);
        recyclerView.setAdapter(adapter);// set adapter on recyclerview

        return view;

    }

}