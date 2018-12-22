package cse.marmara.marunify;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.sql.ResultSet;
import java.util.List;

import cse.marmara.marunify.fragments.MainFragment;
import cse.marmara.marunify.fragments.UserFragment;
import cse.marmara.marunify.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO enclose queries in asynctask
        Utils utils = new Utils(getSupportFragmentManager());
        ResultSet allUsersRs = utils.executeSQLstatement("get all users query");
        List<User> allUsers = utils.parseUserFromResultSet(allUsersRs);

        UserFragment frUserSelect = new UserFragment(getSupportFragmentManager(), allUsers);
        switchFragment(frUserSelect);
    }

    public void switchFragment(Fragment fragment) {
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment)
                .commit();
    }

}
