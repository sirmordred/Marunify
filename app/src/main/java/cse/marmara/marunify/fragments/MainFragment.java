package cse.marmara.marunify.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cse.marmara.marunify.R;
import cse.marmara.marunify.model.Album;
import cse.marmara.marunify.model.Artist;
import cse.marmara.marunify.model.Genre;
import cse.marmara.marunify.model.Playlist;
import cse.marmara.marunify.model.Song;


public class MainFragment extends Fragment {

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private String usrName;
    private FragmentManager frMng;

    private List<Song> songArr;
    private List<Genre> genreArr;
    private List<Playlist> plArr;
    private List<Artist> artArr;
    private List<Album> albArr;

    public MainFragment() {
        // empty constructor
    }

    @SuppressLint("ValidFragment")
    public MainFragment(FragmentManager frMng,
                        List<Song> songArr, List<Genre> genreArr,
                        List<Playlist> plArr, List<Artist> artArr,
                        List<Album> albArr) {
        this.songArr = songArr;
        this.genreArr = genreArr;
        this.plArr = plArr;
        this.artArr = artArr;
        this.albArr = albArr;
        this.frMng = frMng;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ViewPagerAdapter(frMng);

        Fragment ctFagment1 = new SongFragment(songArr);
        Fragment ctFagment2 = new ArtistFragment(frMng, artArr);
        Fragment ctFagment3 = new GenreFragment(frMng, genreArr);
        Fragment ctFagment4 = new AlbumFragment(frMng, albArr);
        Fragment ctFagment5 = new PlaylistFragment(frMng, plArr);

        adapter.addFrag(ctFagment1, "SONG");
        adapter.addFrag(ctFagment2, "ARTIST");
        adapter.addFrag(ctFagment3, "GENRE");
        adapter.addFrag(ctFagment4, "ALBUM");
        adapter.addFrag(ctFagment5, "PLAYLIST");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        // Inflate the xml file for the fragment
        return inflater.inflate(R.layout.activity_main_fragment_view, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        viewPager =  view.findViewById(R.id.viewPager);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);//setting tab over viewpager
    }

    //View Pager fragments setting adapter class
    class ViewPagerAdapter extends FragmentStatePagerAdapter{
        private final List<Fragment> mFragmentList = new ArrayList<>();//fragment arraylist
        private final List<String> mFragmentTitleList = new ArrayList<>();//title arraylist

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }


        //adding fragments and title method
        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

