package sg.edu.nus.iss.ibfb4ssfassessment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.edu.nus.iss.ibfb4ssfassessment.model.Movie;
import sg.edu.nus.iss.ibfb4ssfassessment.service.DatabaseService;
import sg.edu.nus.iss.ibfb4ssfassessment.service.FileService;
import sg.edu.nus.iss.ibfb4ssfassessment.util.Util;

// TODO: Put in the necessary code as described in Task 1 & Task 2
@SpringBootApplication
public class IbfB4SsfAssessmentApplication implements CommandLineRunner {

	@Autowired
	private FileService fileService;

	@Autowired
	private DatabaseService databaseService;

	public static void main(String[] args) {
		SpringApplication.run(IbfB4SsfAssessmentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		/* READ FILE */
		List<Movie> movieList = fileService.readFile(Util.FILE_NAME);

		/* DISPLAY LIST OF MOVIES IN CONSOLE + SAVE EACH MOVIE RECORD */
		for (Movie movie : movieList) {
			System.out.println(">>>>> MOVIE ID: " + movie.getMovieId());
			System.out.println(movie);
			System.out.println("-----------");

			databaseService.saveRecord(movie);
		}

		/* TESTING NUMBER OF MOVIES */
		databaseService.getNumberOfMovies();
	}

}
