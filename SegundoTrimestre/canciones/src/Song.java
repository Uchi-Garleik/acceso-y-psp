import java.util.ArrayList;

public class Song {
    int idSong;
    String title;
    ArrayList<String> verses;

    public Song(int idSong, String title){
        setIdSong(idSong);
        setTitle(title);
    }

    public int getIdSong() {
        return idSong;
    }

    public void setIdSong(int idSong) {
        this.idSong = idSong;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getVerses() {
        return verses;
    }

    public void setVerses(ArrayList<String> verses) {
        this.verses = verses;
    }
}
