package cse.marmara.marunify.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cse.marmara.marunify.R;
import cse.marmara.marunify.Utils;
import cse.marmara.marunify.model.Artist;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {
    private List<Artist> arrayList;
    private FragmentManager frgMng;
    private FragmentActivity act;

    public ArtistAdapter(FragmentManager frgMng, FragmentActivity act, List<Artist> arrayList) {
        this.frgMng = frgMng;
        this.act = act;
        this.arrayList = arrayList;
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        //Setting text over textview
        Artist object = arrayList.get(position);
        if (object != null) {
            holder.mTxtArtName.setText(object.getName());
            holder.mTxtArtAlbumCnt.setText(String.valueOf(object.getArtAlbCount()) + " Album");
            holder.mTxtArtSongCnt.setText(String.valueOf(object.getArtSongCount()) + " Song");
            holder.mMainContainer.setTag(position);
        }
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.item_artist, viewGroup, false);
        return new ArtistViewHolder(mainGroup);

    }


    public class ArtistViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxtArtName;
        public TextView mTxtArtAlbumCnt;
        public TextView mTxtArtSongCnt;
        public LinearLayout mMainContainer;

        public ArtistViewHolder(View view) {
            super(view);
            mMainContainer = view.findViewById(R.id.itemArt);
            mTxtArtName = view.findViewById(R.id.txtArtTitle);
            mTxtArtAlbumCnt = view.findViewById(R.id.txtArtAlbCnt);
            mTxtArtSongCnt = view.findViewById(R.id.txtArtSngCnt);

            ProgressDialog pdialog = new ProgressDialog(act);
            pdialog.setMessage("Opening. Please wait...");
            pdialog.setIndeterminate(true);
            pdialog.setCancelable(false);

            final Utils utils = new Utils(frgMng, pdialog);
            mMainContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    utils.changeFragment(mTxtArtName.getText().toString(), 0);
                }
            });
        }
    }

}