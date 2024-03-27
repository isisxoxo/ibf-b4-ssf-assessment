package sg.edu.nus.iss.ibfb4ssfassessment.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.ibfb4ssfassessment.model.Movie;

@Service
public record FileService() {

    // TODO: Task 1
    public List<Movie> readFile(String fileName) throws FileNotFoundException {

        JsonReader jsonReader = Json.createReader(new FileReader(fileName));
        JsonArray movieArray = jsonReader.readArray();

        List<Movie> movieListing = new LinkedList<>();

        for (int i = 0; i < movieArray.size(); i++) {
            JsonObject movieJson = movieArray.get(i).asJsonObject();
            Movie movie = new Movie();
            movie.setMovieId(movieJson.getInt("Id"));
            movie.setTitle(movieJson.getString("Title"));
            movie.setYear(movieJson.getString("Year"));
            movie.setRated(movieJson.getString("Rated"));

            String released = movieJson.get("Released").toString();
            movie.setReleaseDate(Long.parseLong(released));

            movie.setRunTime(movieJson.getString("Runtime"));
            movie.setGenre(movieJson.getString("Genre"));
            movie.setDirector(movieJson.getString("Director"));
            movie.setRating(Double.parseDouble(movieJson.get("Rating").toString()));

            Date releasedDate = new Date(Long.parseLong(released));
            movie.setFormattedReleaseDate(releasedDate);

            movie.setCount(movieJson.getInt("Count"));

            movieListing.add(movie);
        }

        return movieListing;
    }

}
