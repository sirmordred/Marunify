package cse.marmara.marunify.model;

public class Artist {
    private String name;
    private int ArtAlbCount;
    private int ArtSongCount;

    public Artist(String name, int artAlbCount, int artSongCount) {
        this.name = name;
        ArtAlbCount = artAlbCount;
        ArtSongCount = artSongCount;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArtAlbCount() {
        return ArtAlbCount;
    }

    public void setArtAlbCount(int artAlbCount) {
        ArtAlbCount = artAlbCount;
    }

    public int getArtSongCount() {
        return ArtSongCount;
    }

    public void setArtSongCount(int artSongCount) {
        ArtSongCount = artSongCount;
    }
}
