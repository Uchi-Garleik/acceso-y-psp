import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Singer> singers = new ArrayList<>();
        ArrayList<Song> songs = new ArrayList<>();

        // CREATE PRIMO VICTORIA SONG
            Song songPrimoVictoria = new Song(0, "Sabaton - Primo Victoria");
                ArrayList<String> versesPrimoVictoria = new ArrayList<>();
                    versesPrimoVictoria.add("Through the gates of Hell, as we make our way to Heaven");
                    versesPrimoVictoria.add("Through the Nazi lines - Primo Victoria!");
                    versesPrimoVictoria.add("We've been training for years, now we're ready to strike");
                    versesPrimoVictoria.add("As the great operation begins");
                    versesPrimoVictoria.add("We're the first waves on shore, we're the first ones to fall");
                    versesPrimoVictoria.add("Yet soldiers have fallen before");
                    versesPrimoVictoria.add("In the dawn, they will pay - with their lives as the price!");
                    versesPrimoVictoria.add("History's written today");
                    versesPrimoVictoria.add("In this burning inferno, know that nothing remains");
                    versesPrimoVictoria.add("As our forces advance on the beach");
                    versesPrimoVictoria.add("Aiming for Heaven, though serving in Hell");
                    versesPrimoVictoria.add("Victory's ours, their forces will fall!");
                    versesPrimoVictoria.add("Through the gates of Hell, as we make our way to Heaven");
                    versesPrimoVictoria.add("Through the Nazi lines - Primo Victoria!");
                    versesPrimoVictoria.add("On the 6th of June, on the shores of western Europe");
                    versesPrimoVictoria.add("1944 - D-Day upon us!");
            songPrimoVictoria.setVerses(versesPrimoVictoria);

        songs.add(songPrimoVictoria);

        Scanner scanner = new Scanner(System.in);
            int singerNumber = 0;
            System.out.println("Singer Thread Amount:");
            singerNumber = scanner.nextInt();
        scanner.close();

        for (int i = 0; i < singerNumber; i++) {
            Singer singer = new Singer(i);
            singer.setSong(songPrimoVictoria);
            singers.add(singer);
        }

        for (Singer singer : singers) {
            singer.setSingers(singers);
            singer.start();
        }



    }
}