package cse.marmara.marunify.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cse.marmara.marunify.R;
import cse.marmara.marunify.adapters.AlbumAdapter;
import cse.marmara.marunify.adapters.SongAdapter;
import cse.marmara.marunify.model.Album;
import cse.marmara.marunify.model.Song;

public class AlbumFragment extends Fragment {
    private View view;

    private RecyclerView recyclerView;

    private List<Album> mList;
    private FragmentManager frMng;

    public AlbumFragment() {
        // empty constructor
    }

    @SuppressLint("ValidFragment")
    public AlbumFragment(FragmentManager frMng, List<Album> mList) {
        this.frMng = frMng;
        this.mList = mList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.subfragment_view, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        LinearLayoutManager mLinearLayout = new LinearLayoutManager(getActivity());
        recyclerView
                .setLayoutManager(mLinearLayout);//Linear Items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLinearLayout.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);


        AlbumAdapter adapter = new AlbumAdapter(frMng, getActivity(), mList);
        recyclerView.setAdapter(adapter); // set adapter on recyclerview

        return view;

    }

}