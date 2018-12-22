package cse.marmara.marunify;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cse.marmara.marunify.model.Song;
import cse.marmara.marunify.model.User;

public class Utils {
    private FragmentManager fr_mng;
    private static Connection con;

    public Utils(FragmentManager fr_mng) {
        this.fr_mng = fr_mng;
        ConnectToDatabase ctb = new ConnectToDatabase(); // make connection to database
        ctb.execute("");
    }

    public void changeFragment(String param, int type) {
        switch (type) {
            case 0:
                // Artist clicked, list all songs of that Artist
                break;
            case 1:
                // Genre clicked, list all songs of that Genre
                break;
            case 2:
                // Album clicked, list all songs of that Album
                break;
            case 3:
                // Playlist clicked, list all songs of that Playlist
                break;
            case 4: // user clicked, list all song, genres, albums, artist but list only playlist who assigned to this user
                // TODO Execure sql and create list of songs, albums etc and instantiate MainFragment()
                break;
            default:
                    break;
        }
    }

    public void goToFragment(Fragment fragment) {
        FragmentTransaction transaction = fr_mng.beginTransaction();
        transaction.replace(R.id.flContent, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public List<Song> parseSongFromResultSet(ResultSet rs) {
        List<Song> retList = new ArrayList<>();
        try {
            while (rs.next() ) {
                String sngTitle = rs.getString(1);
                String sngArt = rs.getString(2);
                String sngArtCountry = rs.getString(3);
                String sngDur = rs.getString(4);

                retList.add(new Song(sngTitle,sngArt,sngArtCountry,sngDur));
            }
            return retList;
        } catch (SQLException se) {
            return retList;
        }
    }

    public List<User> parseUserFromResultSet(ResultSet rs) {
        List<User> retList = new ArrayList<>();
        try {
            while (rs.next() ) {
                String usrName = rs.getString(0);
                retList.add(new User(usrName));
            }
            return retList;
        } catch (SQLException se) {
            return retList;
        }
    }

    public ResultSet executeSQLstatement(String query) { // call it from asynctask
        if (con != null) {
            try {
                Statement stmt = con.createStatement();
                return stmt.executeQuery(query);
            } catch (SQLException e) {
                return null;
            }
        }
        return null;
    }

    public static class ConnectToDatabase extends AsyncTask<String,String,Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                if (con == null) {
                    con = connectToDB();        // Connect to database
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    @SuppressLint("NewApi")
    private static Connection connectToDB() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String ConnectionURL = "jdbc:jtds:sqlserver://10.0.2.2:1433/MARUNIFY;user=marunex;password=marunex123;integratedSecurity=true;encrypt=false;";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (Exception se) {
            se.printStackTrace();
        }
        return connection;
    }

    private static void closeConnectionToDb() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            con = null;
        }
    }

}
