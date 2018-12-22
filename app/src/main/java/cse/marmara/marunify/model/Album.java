package cse.marmara.marunify.model;

public class Album {
    public Album(String title, String albArtist) {
        this.title = title;
        this.albArtist = albArtist;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbArtist() {
        return albArtist;
    }

    public void setAlbArtist(String albArtist) {
        this.albArtist = albArtist;
    }

    private String title;
    private String albArtist;


}
