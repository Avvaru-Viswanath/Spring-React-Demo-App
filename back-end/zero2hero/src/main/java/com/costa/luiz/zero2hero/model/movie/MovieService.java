package com.costa.luiz.zero2hero.model.movie;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    private final ReviewRepository reviewRepository;

    public List<Movie> findAll() {
        return movieRepository.findAll()
                .stream()
                .peek(movie -> {
                    List<Review> allByMovie = reviewRepository.findAllByMovie(movie);
                    movie.setReviews(allByMovie);
                })
                .collect(Collectors.toUnmodifiableList());
    }

    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void deleteReviewsToMovie(Long id) {
        reviewRepository.deleteAllByMovie_Id(id);
    }

    public void update(Movie movie) {
        movieRepository.save(movie);
    }

    public void newMovie(Movie movie) {
        if (nonNull(movie.getId())) {
            throw new IllegalArgumentException("Id must be null");
        }
        movieRepository.save(movie);
    }
}
