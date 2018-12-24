package cse.marmara.marunify.adapters;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.media.MediaPlayer;


import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import cse.marmara.marunify.MainActivity;
import cse.marmara.marunify.R;
import cse.marmara.marunify.model.Song;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private List<Song> arrayList;

    private static MediaPlayer mediaPlayer = null;
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
            mediaPlayer = null;
        }
    };

    public SongAdapter(List<Song> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        //Setting text over textview
        Song object = arrayList.get(position);
        if (object != null) {
            holder.mTxtSngTitle.setText(object.getTitle());
            holder.mTxtSngDur.setText(object.getDuration());
            holder.mTxtSngArt.setText(object.getSongArt());
            holder.mTxtSngAlb.setText(object.getSongArtCountry());
            holder.mMainContainer.setTag(position);

        }
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.item_song, viewGroup, false);
        return new SongViewHolder(mainGroup);

    }


    public class SongViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxtSngTitle;
        public TextView mTxtSngDur;
        public TextView mTxtSngArt;
        public TextView mTxtSngAlb;
        public LinearLayout mMainContainer;

        public SongViewHolder(View view) {
            super(view);
            mMainContainer = view.findViewById(R.id.itemSong);
            mTxtSngTitle = view.findViewById(R.id.txtSongTitle);
            mTxtSngDur = view.findViewById(R.id.txtSongDur);
            mTxtSngArt = view.findViewById(R.id.txtSongArtist);
            mTxtSngAlb = view.findViewById(R.id.txtSongAlbum);
            mMainContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playMp3(mTxtSngTitle.getText().toString());
                    Log.e("SONG",mTxtSngTitle.getText().toString()); // log for now
                }
            });
        }
    }

    public void playMp3(String mp3Title) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        try {
            FileInputStream mp3Fd = new FileInputStream(new File(MainActivity.musicPath
                    + mp3Title + ".mp3"));
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(mp3Fd.getFD());
            mediaPlayer.setLooping(false);
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(onCompletionListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}