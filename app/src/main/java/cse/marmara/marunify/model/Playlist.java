package cse.marmara.marunify.model;

public class Playlist {
    private String name;
    private String plSongCnt;

    public Playlist(String name, String plSongCnt) {
        this.name = name;
        this.plSongCnt = plSongCnt;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlSongCnt() {
        return plSongCnt;
    }

    public void setPlSongCnt(String plSongCnt) {
        this.plSongCnt = plSongCnt;
    }
}
