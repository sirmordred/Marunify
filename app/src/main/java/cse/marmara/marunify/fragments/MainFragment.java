package cse.marmara.marunify.fragments;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        List<Song> songArr = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            songArr.add(new Song(i,"Elbet bir gün"+i,"03:30"));
        }

        List<Genre> genreArr = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            genreArr.add(new Genre(i, "Album"+i));
        }

        List<Playlist> plArr = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            plArr.add(new Playlist(i, "Playlistim"+i));
        }

        List<Artist> artArr = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            artArr.add(new Artist(i,"Canbay","Türkiye"));
        }

        List<Album> albArr = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            albArr.add(new Album(i,"MyAlbum"+i, "03.12.2018", "Multi"));
        }

        Fragment ctFagment1 = new SongFragment(songArr);
        Fragment ctFagment2 = new ArtistFragment(artArr);
        Fragment ctFagment3 = new GenreFragment(genreArr);
        Fragment ctFagment4 = new AlbumFragment(albArr);
        Fragment ctFagment5 = new PlaylistFragment(plArr);

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

        //Implementing tab selected listener over tablayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());//setting current selected item over viewpager
                switch (tab.getPosition()) {
                    case 0:
                        Log.e("TAG","TAB1");
                        break;
                    case 1:
                        Log.e("TAG","TAB2");
                        break;
                    case 2:
                        Log.e("TAG","TAB3");
                        break;
                    case 3:
                        Log.e("TAG","TAB4");
                        break;
                    case 4:
                        Log.e("TAG","TAB5");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

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

