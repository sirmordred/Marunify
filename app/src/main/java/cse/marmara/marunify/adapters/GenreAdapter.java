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
import cse.marmara.marunify.model.Genre;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {
    private List<Genre> arrayList;
    private FragmentManager frgMng;

    public GenreAdapter(FragmentManager frgMng, List<Genre> arrayList) {
        this.frgMng = frgMng;
        this.arrayList = arrayList;
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        //Setting text over textview
        Genre object = arrayList.get(position);
        if (object != null) {
            holder.mTxtGnrTitle.setText(object.getTitle());
            holder.mTxtGnrSngCnt.setText(object.getGenreSngCnt());
            holder.mMainContainer.setTag(position);
        }
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.item_genre, viewGroup, false);
        return new GenreViewHolder(mainGroup);

    }


    public class GenreViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxtGnrTitle;
        public TextView mTxtGnrSngCnt;
        public LinearLayout mMainContainer;

        public GenreViewHolder(View view) {
            super(view);
            mMainContainer = view.findViewById(R.id.itemGenre);
            mTxtGnrTitle = view.findViewById(R.id.txtGenreTitle);
            mTxtGnrSngCnt = view.findViewById(R.id.txtGenreSongCount);

            final Utils utils = new Utils(frgMng);
            mMainContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    utils.changeFragment(mTxtGnrTitle.getText().toString(), 1);
                }
            });
        }
    }

}