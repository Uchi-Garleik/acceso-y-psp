import java.util.ArrayList;

public class Singer extends Thread {
    private int idSinger;
    
    private ArrayList<Singer> singers;
    private Song song;
    private int versesToSing;
    private static int versesToSingLeftToDistribute;
    private static int verseCounter = 0;
    @Override
    public synchronized void run() {
        System.out.println(singers.size());
        versesToSing = song.getVerses().size() / singers.size();
        for (int i = 0; i < getVersesToSing(); i++) {

            if (getIdSinger() == 0 && verseCounter == 0){
                System.out.println(song.getVerses().get(verseCounter));
                if (getIdSinger() == singers.size()-1){
                    verseCounter++;
                    singers.get(0).interrupt();
                }else{
                    verseCounter++;
                    singers.get(getIdSinger()+1).interrupt();
                }
                continue;
            }

            try {
                wait(1000);
            } catch (InterruptedException e) {
                System.out.println(song.getVerses().get(verseCounter));
                if (getIdSinger() == singers.size()-1){
                    verseCounter++;
                    singers.get(0).interrupt();
                }else{
                    verseCounter++;
                    singers.get(getIdSinger()+1).interrupt();
                }
            }
        }
    }

    public int getVersesToSing() {
        return versesToSing;
    }

    public void setVersesToSing(int versesToSing) {
        this.versesToSing = versesToSing;
    }

    public Singer(int idSinger){
        this.idSinger = idSinger;
    }

    public int getIdSinger() {
        return idSinger;
    }

    public void setIdSinger(int idSinger) {
        this.idSinger = idSinger;
    }

    public ArrayList<Singer> getSingers() {
        return singers;
    }

    public void setSingers(ArrayList<Singer> singers) {
        this.singers = singers;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public String toStrings() {
        return "Singer{" +
                "idSinger=" + idSinger +
                ", singersSize=" + singers.size() +
                ", songTitle=" + song.getTitle() +
                ", versesToSing=" + versesToSing +
                ", versesToSingLeftToDistribute=" + versesToSingLeftToDistribute +
                '}';
    }
}