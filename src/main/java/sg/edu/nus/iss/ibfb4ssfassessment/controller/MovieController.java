package sg.edu.nus.iss.ibfb4ssfassessment.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.ibfb4ssfassessment.model.Login;
import sg.edu.nus.iss.ibfb4ssfassessment.model.Movie;
import sg.edu.nus.iss.ibfb4ssfassessment.service.DatabaseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MovieController {

    @Autowired
    private DatabaseService databaseService;

    // TODO: Task 8
    @GetMapping(path = "/movielist")
    public ModelAndView displayMovies(HttpSession session) throws ParseException {

        ModelAndView mav = new ModelAndView();

        Login login = (Login) session.getAttribute("login");
        if (login == null) {

            mav.setViewName("redirect:/login");

        } else {

            mav.addObject("login", login);

            mav.setViewName("view2");

            List<Movie> movies = databaseService.getAllMovies();
            mav.addObject("movies", movies);
        }

        return mav;
    }

    // TODO: Task 9
    @PostMapping(path = "/book/{movieId}")
    public String bookMovie(@PathVariable("movieId") String movieId, @RequestBody MultiValueMap<String, String> login,
            Model model)
            throws NumberFormatException, ParseException {

        Movie movie = databaseService.getMovieById(Integer.parseInt(movieId));
        String movieRated = movie.getRated();

        String birthDate = login.getFirst("birthDate");

        LocalDate birthDateLocal = LocalDate.parse(birthDate);
        LocalDate localDateR = LocalDate.now().minusYears(18);
        LocalDate localDatePG = LocalDate.now().minusYears(13);

        if ((birthDateLocal.isAfter(localDatePG)) && (movieRated.equals("PG-13"))) {
            return "BookError";
        } else if ((birthDateLocal.isAfter(localDateR)) && (movieRated.equals("R"))) {
            return "BookError";
        } else {
            model.addAttribute("movieTitle", movie.getTitle());

            // Increase movie count
            databaseService.increaseMovieCount(movieId);

            return "BookSuccess";
        }
    }

}