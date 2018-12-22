package cse.marmara.marunify.model;

public class Song {
    private String title;
    private String songArt;
    private String songArtCountry;
    private String duration;

    public Song(String title, String songArt, String songArtCountry, String duration) {
        this.title = title;
        this.songArt = songArt;
        this.songArtCountry = songArtCountry;
        this.duration = duration;
    }

    public String getDuration() {

        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSongArt() {
        return songArt;
    }

    public void setSongArt(String songArt) {
        this.songArt = songArt;
    }

    public String getSongArtCountry() {
        return songArtCountry;
    }

    public void setSongArtCountry(String songArtCountry) {
        this.songArtCountry = songArtCountry;
    }
}
