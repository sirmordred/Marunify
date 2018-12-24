package cse.marmara.marunify;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.List;

import cse.marmara.marunify.fragments.UserFragment;
import cse.marmara.marunify.model.User;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pd;

    public static String musicPath;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AsyncTask<Void,Void,List<User>>() {
            @Override
            protected void onPreExecute() {
                if (pd == null) {
                    pd = new ProgressDialog(MainActivity.this);
                    pd.setMessage("Opening. Please wait...");
                    pd.setIndeterminate(true);
                    pd.setCancelable(false);
                }
                pd.show();
                super.onPreExecute();
            }

            @Override
            protected List<User> doInBackground(Void... params) {
                copyAssets(getApplicationContext());

                ResultSet allUsersRs = Utils.executeSql("select * FROM GetAllUsers");
                if (allUsersRs != null) {
                    return Utils.parseUserFromResultSet(allUsersRs);
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<User> t) {
                if (pd != null) {
                    pd.dismiss();
                }
                if (t != null && t.size() > 0) {
                    UserFragment frUserSelect = new UserFragment(getSupportFragmentManager(), t);
                    switchFragment(frUserSelect);
                }

                super.onPostExecute(t);
            }
        }.execute();


    }

    public void switchFragment(Fragment fragment) {
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment)
                .commit();
    }

    private void copyAssets(Context ctx) {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) {
            for(String filename : files) {
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = assetManager.open(filename);
                    String outDir = ctx.getFilesDir().getAbsolutePath() + File.separator + "musics" + File.separator;
                    File musicFolder = new File(outDir);
                    if (!musicFolder.exists()) {
                        musicFolder.mkdir();
                    }
                    musicPath = outDir;
                    File outFile = new File(outDir, filename);
                    out = new FileOutputStream(outFile);

                    byte[] buffer = new byte[1024];
                    int read;
                    while((read = in.read(buffer)) != -1){
                        out.write(buffer, 0, read);
                    }
                } catch(IOException e) {
                    Log.e("tag", "Failed to copy asset file: " + filename, e);
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        in = null;
                    }

                    if (out != null) {
                        try {
                            out.flush();
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        out = null;
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        Utils.closeDbConn();
        super.onDestroy();
    }

}
