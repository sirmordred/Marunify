package cse.marmara.marunify;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cse.marmara.marunify.fragments.MainFragment;
import cse.marmara.marunify.fragments.SongFragment;
import cse.marmara.marunify.model.Album;
import cse.marmara.marunify.model.Artist;
import cse.marmara.marunify.model.Genre;
import cse.marmara.marunify.model.Playlist;
import cse.marmara.marunify.model.Song;
import cse.marmara.marunify.model.User;

public class Utils {
    private FragmentManager fr_mng;
    private ProgressDialog p;
    private static Connection cn = null;

    private List<Album> albList = new ArrayList<>();
    private List<Artist> artList = new ArrayList<>();
    private List<Genre> gnList = new ArrayList<>();
    private List<Playlist> plList = new ArrayList<>();
    private List<Song> sngList = new ArrayList<>();

    public Utils(FragmentManager fr_mng, ProgressDialog p) {
        this.fr_mng = fr_mng;
        this.p = p;
    }

    public void changeFragment(String param, int type) {
        if (type == 4) {
            goToMain(param);
        } else {
            goToSongs(param, type);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void goToSongs(final String parameter, final int type) {
        new AsyncTask<Void,Void,List<Song>>() {
            @Override
            protected void onPreExecute() {
                p.show();
                super.onPreExecute();
            }

            @Override
            protected List<Song> doInBackground(Void... params) {
                ResultSet rSet = null;
                switch (type) {
                    case 0:
                        String queryStr = "exec GetSongsFromArtist '" + parameter + "'";
                        rSet = Utils.executeSql(queryStr);
                        break;
                    case 1:
                        String queryStr2 = "exec GetSongsFromGenre '" + parameter + "'";
                        rSet = Utils.executeSql(queryStr2);
                        break;
                    case 2:
                        String queryStr3 = "exec GetSongsFromAlbum '" + parameter + "'";
                        rSet = Utils.executeSql(queryStr3);
                        break;
                    case 3:
                        String queryStr4 = "exec GetSongsFromPlaylist '" + parameter + "'";
                        rSet = Utils.executeSql(queryStr4);
                        break;
                    default:
                            break;
                }
                List<Song> sngL = new ArrayList<>();
                if (rSet != null) {
                    sngL.addAll(parseSongFromResultSet(rSet));
                }
                return sngL;
            }

            @Override
            protected void onPostExecute(List<Song> t) {
                if (p != null) {
                    p.dismiss();
                }
                if (t.size() > 0) {
                    SongFragment frSongFrg = new SongFragment(t);
                    goToFragment(frSongFrg);
                }
                super.onPostExecute(t);
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void goToMain(final String parameter) {
        new AsyncTask<Void,Void,Boolean>() {
            @Override
            protected void onPreExecute() {
                p.show();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                ResultSet allUsersRs = Utils.executeSql("SELECT * FROM GetAlbumInfo");
                if (allUsersRs != null) {
                    albList.addAll(parseAlbumFromResultSet(allUsersRs));
                }
                ResultSet allUsersRs2 = Utils.executeSql("SELECT * FROM GetArtistInfo");
                if (allUsersRs2 != null) {
                    artList.addAll(parseArtistFromResultSet(allUsersRs2));
                }
                ResultSet allUsersRs3 = Utils.executeSql("SELECT * FROM GetGenreInfo");
                if (allUsersRs3 != null) {
                    gnList.addAll(parseGenreFromResultSet(allUsersRs3));
                }
                String queryStrUsrPl = "exec GetPlaylistFromUser '" + parameter + "'";
                ResultSet allUsersRs4 = Utils.executeSql(queryStrUsrPl);
                if (allUsersRs4 != null) {
                    plList.addAll(parsePlaylistFromResultSet(allUsersRs4));
                }
                ResultSet allUsersRs5 = Utils.executeSql("SELECT * FROM GetAllSongs");
                if (allUsersRs5 != null) {
                    sngList.addAll(parseSongFromResultSet(allUsersRs5));
                }
                return sngList.size() > 0 && plList.size() > 0 &&
                        gnList.size() > 0 && artList.size() > 0 && albList.size() > 0;
            }

            @Override
            protected void onPostExecute(Boolean t) {
                if (p != null) {
                    p.dismiss();
                }
                if (t) {
                    MainFragment frUserSelect = new MainFragment(fr_mng, sngList, gnList,
                            plList, artList, albList);
                    goToFragment(frUserSelect);
                }
                super.onPostExecute(t);
            }
        }.execute();
    }

    public void goToFragment(Fragment fragment) {
        FragmentTransaction transaction = fr_mng.beginTransaction();
        transaction.replace(R.id.flContent, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static List<Song> parseSongFromResultSet(ResultSet rs) {
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

    public static List<User> parseUserFromResultSet(ResultSet rs) {
        List<User> retList = new ArrayList<>();
        try {
            while (rs.next() ) {
                String usrName = rs.getString(1);
                retList.add(new User(usrName));
            }
            return retList;
        } catch (SQLException se) {
            se.printStackTrace();
            return retList;
        }
    }

    public static List<Playlist> parsePlaylistFromResultSet(ResultSet rs) {
        List<Playlist> retList = new ArrayList<>();
        try {
            while (rs.next() ) {
                String plName = rs.getString(1);
                retList.add(new Playlist(plName));
            }
            return retList;
        } catch (SQLException se) {
            se.printStackTrace();
            return retList;
        }
    }

    public static List<Album> parseAlbumFromResultSet(ResultSet rs) {
        List<Album> retList = new ArrayList<>();
        try {
            while (rs.next() ) {
                String albTitle = rs.getString(1);
                String albOwner = rs.getString(2);
                retList.add(new Album(albTitle, albOwner));
            }
            return retList;
        } catch (SQLException se) {
            se.printStackTrace();
            return retList;
        }
    }

    public static List<Genre> parseGenreFromResultSet(ResultSet rs) {
        List<Genre> retList = new ArrayList<>();
        try {
            while (rs.next() ) {
                String gnrTitle = rs.getString(1);
                String gnrSngCnt = rs.getString(2);
                retList.add(new Genre(gnrTitle, gnrSngCnt));
            }
            return retList;
        } catch (SQLException se) {
            se.printStackTrace();
            return retList;
        }
    }


    public static List<Artist> parseArtistFromResultSet(ResultSet rs) {
        List<Artist> retList = new ArrayList<>();
        try {
            while (rs.next() ) {
                String artName = rs.getString(1);
                String artAlbCnt = rs.getString(2);
                String artSngCnt = rs.getString(3);

                retList.add(new Artist(artName,Integer.parseInt(artAlbCnt),Integer.parseInt(artSngCnt)));
            }
            return retList;
        } catch (SQLException se) {
            se.printStackTrace();
            return retList;
        }
    }

    @SuppressLint("NewApi")
    public static ResultSet executeSql(String query) {
        if (cn == null) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                String ConnectionURL = "jdbc:jtds:sqlserver://10.0.2.2:1433/MARUNIFY;user=marunex;password=marunex123;integratedSecurity=true;encrypt=false;";
                cn = DriverManager.getConnection(ConnectionURL);
            } catch (Exception se) {
                se.printStackTrace();
                return null;
            }
        }
        try {
            Statement stmt = cn.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeDbConn() {
        if (cn != null) {
            try {
                cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
