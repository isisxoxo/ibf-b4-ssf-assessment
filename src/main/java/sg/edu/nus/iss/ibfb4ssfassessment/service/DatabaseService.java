package sg.edu.nus.iss.ibfb4ssfassessment.service;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.ibfb4ssfassessment.model.Movie;
import sg.edu.nus.iss.ibfb4ssfassessment.util.Util;

@Service
public class DatabaseService {

    @Autowired
    @Qualifier(Util.REDIS_TWO)
    RedisTemplate<String, String> template;

    HashOperations<String, String, String> hashOps;

    // TODO: Task 2 (Save to Redis Map)
    public void saveRecord(Movie movie) {

        hashOps = template.opsForHash();
        Integer movieId = movie.getMovieId();
        JsonObject movieJson = movieToJson(movie);
        hashOps.put(Util.KEY_MOVIES, movieId.toString(), movieJson.toString());

    }

    // TODO: Task 3 (Map or List - comment where necesary)
    public long getNumberOfMovies() {

        hashOps = template.opsForHash();
        long numberOfMovies = hashOps.size(Util.KEY_MOVIES);
        System.out.println(">>>>> NUMBER OF MOVIES: " + numberOfMovies); // For testing
        return numberOfMovies;
    }

    // public Movie getMovie(Integer index) {
    // return repo.getMovie(index);
    // }

    // TODO: Task 4 (Map)
    public Movie getMovieById(Integer movieId) throws ParseException {

        hashOps = template.opsForHash();
        String movieString = hashOps.get(Util.KEY_MOVIES, movieId.toString());
        JsonObject movieJson = stringToJson(movieString);
        Movie movie = jsonToMovie(movieJson);

        return movie;
    }

    // TODO: Task 5
    public List<Movie> getAllMovies() throws ParseException {

        hashOps = template.opsForHash();
        List<String> allMoviesString = hashOps.values(Util.KEY_MOVIES);
        List<Movie> allMovies = new LinkedList<>();

        for (String m : allMoviesString) {
            JsonObject movieJson = stringToJson(m);
            Movie movie = jsonToMovie(movieJson);
            allMovies.add(movie);
        }

        return allMovies;
    }

    // TODO: Task 9
    public void increaseMovieCount(String movieId) throws ParseException {

        hashOps = template.opsForHash();
        String value = hashOps.get(Util.KEY_MOVIES, movieId);
        Movie movie = jsonToMovie(stringToJson(value));
        Integer count = movie.getCount();
        movie.setCount(count + 1);
        JsonObject movieJson = movieToJson(movie);
        hashOps.put(Util.KEY_MOVIES, movieId, movieJson.toString());

    }

    // Movie -> JSON
    public JsonObject movieToJson(Movie movie) {

        JsonObject movieJson = Json.createObjectBuilder()
                .add("movieId", movie.getMovieId())
                .add("title", movie.getTitle())
                .add("year", movie.getYear())
                .add("rated", movie.getRated())
                .add("releaseDate", movie.getReleaseDate())
                .add("runTime", movie.getRunTime())
                .add("genre", movie.getGenre())
                .add("director", movie.getDirector())
                .add("rating", movie.getRating())
                .add("formattedReleaseDate", movie.getFormattedReleaseDate().toString())
                .add("count", movie.getCount())
                .build();

        return movieJson;
    }

    // String -> JSON
    public JsonObject stringToJson(String movieString) {

        JsonReader jsonReader = Json.createReader(new StringReader(movieString));
        JsonObject movieJson = jsonReader.readObject();
        return movieJson;
    }

    // JSON -> Movie
    public Movie jsonToMovie(JsonObject movieJson) throws ParseException {

        Movie movie = new Movie();
        movie.setMovieId(movieJson.getInt("movieId"));
        movie.setTitle(movieJson.getString("title"));
        movie.setYear(movieJson.getString("year"));
        movie.setRated(movieJson.getString("rated"));

        String releaseDateString = movieJson.get("releaseDate").toString();
        movie.setReleaseDate(Long.parseLong(releaseDateString));

        movie.setRunTime(movieJson.getString("runTime"));
        movie.setGenre(movieJson.getString("genre"));
        movie.setDirector(movieJson.getString("director"));
        movie.setRating(Double.parseDouble(movieJson.get("rating").toString()));

        String formattedDateString = movieJson.getString("formattedReleaseDate");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        Date formattedReleaseDate = sdf.parse(formattedDateString);
        movie.setFormattedReleaseDate(formattedReleaseDate);

        movie.setCount(movieJson.getInt("count"));

        return movie;
    }
}
