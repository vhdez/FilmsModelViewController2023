package com.example.filmsmvc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class BoxOfficeFilm extends Film {

    // Fields
    private static ArrayList<BoxOfficeFilm> boxOfficeFilms;
    private static int currentFilmNumber;
    private int peak;

    // Constructors
    BoxOfficeFilm(int rank, String title, int releaseYear, long gross, int peak) {
        super(rank, title, gross, releaseYear, "Box Office");
        this.peak = peak;

        // store the new object in the boxOfficeFilms ArrayList
        if (boxOfficeFilms == null) {
            boxOfficeFilms = new ArrayList<BoxOfficeFilm>();
        }
        boxOfficeFilms.add(this);
    }

    static void initialize() {
        read();
        getMyController().updateBoxOfficeFilmsUI(getFirstFilm(), 1, getNumberOfFilms());
    }

    // Setters/Getters

    public int getPeak() {
        return peak;
    }

    public void setPeak(int peak) {
        this.peak = peak;
    }

    static public int getCurrentFilmNumber() {
        return currentFilmNumber;
    }

    static public int getNumberOfFilms() {
        return boxOfficeFilms.size();
    }

    // Methods
    public String toString() {
        String description = "Box Office rank #" + this.getRank();
        description = description + " is \"" + this.getTitle() + "\"" ;
        description = description + " from year " + this.getYear();
        description = description + " grossing $" + this.getGross();
        description = description + " and peaking at rank #" + this.getPeak();
        return description;
    }

    static void read() {
        String dataFilePath = "BoxOfficeFilmData";
        // try to create Scanner
        Scanner scanner = null;
        try {
            File file = new File(dataFilePath);
            scanner = new Scanner(file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problem opening file: " + dataFilePath);
        }
        if (scanner == null) {
            // I can't scan without a scanner, so we're done.
            return;
        }

        // Read from each line in data file until there are no more
        while (scanner.hasNext()) {
            String next = scanner.nextLine();
            // Construct a new scanner for each to get its tokens
            Scanner lineScanner = new Scanner(next);
            // Data tokens are separated by tabs
            lineScanner.useDelimiter("\t");

            // There are 5 data tokens that we need for each BoxOfficeFilm
            int ranking = lineScanner.nextInt();
            int peak = lineScanner.nextInt();
            String title = lineScanner.next();
            long revenue = lineScanner.nextLong();
            int year = lineScanner.nextInt();

            Film film = new BoxOfficeFilm(ranking, title, year, revenue, peak);
        }
    }

    static public BoxOfficeFilm getFirstFilm() {
        currentFilmNumber = 1;
        return boxOfficeFilms.get(currentFilmNumber - 1);
    }

    static public BoxOfficeFilm getNextFilm() {
        if (currentFilmNumber < boxOfficeFilms.size()) {
            currentFilmNumber = currentFilmNumber + 1;
        } else {
            currentFilmNumber = 1;
        }
        return boxOfficeFilms.get(currentFilmNumber - 1);
    }

    static public BoxOfficeFilm getPreviousFilm() {
        if (currentFilmNumber > 1) {
            currentFilmNumber = currentFilmNumber - 1;
        } else {
            currentFilmNumber = boxOfficeFilms.size();
        }
        return boxOfficeFilms.get(currentFilmNumber - 1);
    }

    static void previous() {
        getMyController().updateBoxOfficeFilmsUI(getPreviousFilm(), getCurrentFilmNumber(), getNumberOfFilms());
    }

    static void next() {
        getMyController().updateBoxOfficeFilmsUI(getNextFilm(), getCurrentFilmNumber(), getNumberOfFilms());
    }
}
