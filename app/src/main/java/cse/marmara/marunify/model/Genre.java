package cse.marmara.marunify.model;

public class Genre {
    private String title;
    private String genreSngCnt;

    public Genre(String title, String genreSngCnt) {
        this.title = title;
        this.genreSngCnt = genreSngCnt;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenreSngCnt() {
        return genreSngCnt;
    }

    public void setGenreSngCnt(String genreSngCnt) {
        this.genreSngCnt = genreSngCnt;
    }
}
