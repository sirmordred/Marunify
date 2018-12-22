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
import cse.marmara.marunify.model.User;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private List<User> arrayList;
    private FragmentManager frMng;

    public UserAdapter(FragmentManager frMng, List<User> arrayList) {
        this.frMng = frMng;
        this.arrayList = arrayList;
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        //Setting text over textview
        User object = arrayList.get(position);
        if (object != null) {
            holder.mTxtUserName.setText(object.getUsername());
            holder.mMainContainer.setTag(position);
        }
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.item_user, viewGroup, false);
        return new UserAdapter.UserViewHolder(mainGroup);

    }


    public class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxtUserName;
        public LinearLayout mMainContainer;

        public UserViewHolder(View view) {
            super(view);
            mMainContainer = view.findViewById(R.id.itemUser);
            mTxtUserName = view.findViewById(R.id.txtUserName);

            final Utils utils = new Utils(frMng);
            mMainContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    utils.changeFragment(mTxtUserName.getText().toString(), 4);
                }
            });
        }
    }
}
