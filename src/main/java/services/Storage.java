package services;

import models.Movie;

public class Storage {

    // Array containing movies in the system
    public static Movie[] movies = {

        new Movie(
            "Avatar",
            "Science Fiction",
            "PG-13",
            162,
            "Daily at 2:00 PM",
            "A journey through the world of Pandora."
        ),

        new Movie(
            "Titanic",
            "Romance",
            "PG-13",
            195,
            "Daily at 4:00 PM",
            "A romantic story aboard a famous ship."
        ),

        new Movie(
            "The Avengers",
            "Action",
            "PG-13",
            143,
            "Daily at 6:00 PM",
            "Heroes come together to save the world."
        ),

        new Movie(
            "Spider-Man",
            "Action",
            "PG-13",
            121,
            "Daily at 8:00 PM",
            "A young hero protects his city."
        ),

        new Movie(
            "Black Panther",
            "Action",
            "PG-13",
            134,
            "Daily at 10:00 AM",
            "A king protects the nation of Wakanda."
        ),

        new Movie(
            "Frozen",
            "Animation",
            "PG",
            102,
            "Daily at 12:00 PM",
            "Two sisters discover the power of love."
        ),

        new Movie(
            "Inception",
            "Science Fiction",
            "PG-13",
            148,
            "Daily at 9:00 PM",
            "A team enters people's dreams."
        )
    };

}
