package cse.marmara.marunify.adapters;

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
import cse.marmara.marunify.model.Album;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {
    private List<Album> arrayList;
    private FragmentManager frgMng;

    public AlbumAdapter(FragmentManager frgMng, List<Album> arrayList) {
        this.frgMng = frgMng;
        this.arrayList = arrayList;
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        //Setting text over textview
        Album object = arrayList.get(position);
        if (object != null) {
            holder.mTxtAlbTitle.setText(object.getTitle());
            holder.mTxtAlbOwner.setText(object.getAlbArtist());
            holder.mMainContainer.setTag(position);
        }
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.item_album, viewGroup, false);
        return new AlbumViewHolder(mainGroup);

    }


    public class AlbumViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxtAlbTitle;
        public TextView mTxtAlbOwner;
        public LinearLayout mMainContainer;

        public AlbumViewHolder(View view) {
            super(view);
            mMainContainer = view.findViewById(R.id.itemAlbum);
            mTxtAlbTitle = view.findViewById(R.id.txtAlbumTitle);
            mTxtAlbOwner = view.findViewById(R.id.txtAlbumOwner);

            final Utils utils = new Utils(frgMng);
            mMainContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    utils.changeFragment(mTxtAlbTitle.getText().toString(), 2);
                }
            });
        }
    }

}