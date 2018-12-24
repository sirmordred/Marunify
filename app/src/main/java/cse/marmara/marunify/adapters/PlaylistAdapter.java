package cse.marmara.marunify.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.FloatRange;
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
import cse.marmara.marunify.model.Playlist;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private List<Playlist> arrayList;
    private FragmentManager frgMng;
    private FragmentActivity act;

    public PlaylistAdapter(FragmentManager frgMng, FragmentActivity act, List<Playlist> arrayList) {
        this.frgMng = frgMng;
        this.act = act;
        this.arrayList = arrayList;
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        //Setting text over textview
        Playlist object = arrayList.get(position);
        if (object != null) {
            holder.mTxtPlTitle.setText(object.getName());
            holder.mMainContainer.setTag(position);
        }
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.item_playlist, viewGroup, false);
        return new PlaylistViewHolder(mainGroup);

    }


    public class PlaylistViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxtPlTitle;
        public LinearLayout mMainContainer;

        public PlaylistViewHolder(View view) {
            super(view);
            mMainContainer = view.findViewById(R.id.itemPlaylist);
            mTxtPlTitle = view.findViewById(R.id.txtPlaylistTitle);

            ProgressDialog pdialog = new ProgressDialog(act);
            pdialog.setMessage("Opening. Please wait...");
            pdialog.setIndeterminate(true);
            pdialog.setCancelable(false);

            final Utils utils = new Utils(frgMng, pdialog);
            mMainContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    utils.changeFragment(mTxtPlTitle.getText().toString(), 3);
                }
            });
        }
    }

}