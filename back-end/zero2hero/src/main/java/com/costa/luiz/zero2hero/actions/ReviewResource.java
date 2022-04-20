package com.costa.luiz.zero2hero.actions;

import com.costa.luiz.zero2hero.model.movie.Review;
import com.costa.luiz.zero2hero.model.movie.dto.AuthorDto;
import com.costa.luiz.zero2hero.repository.ReviewRepository;
import com.costa.luiz.zero2hero.model.movie.dto.MovieMapper;
import com.costa.luiz.zero2hero.model.movie.dto.ReviewDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/reviews")
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name="Reviews")
public class ReviewResource {

    private final ReviewRepository reviewRepository;

    private final MovieMapper movieMapper;

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBy(@PathVariable Long id) {
        log.info("The review {} will be deleted", id);
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            review.setArchived(true);
            reviewRepository.save(review);
        }
    }

    @GetMapping
    public List<ReviewDto> findAll() {
        log.info("Looking for all reviews");
        return reviewRepository.findAll().stream()
                .map(this::getReviewDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @GetMapping(path = "/{id}")
    public ReviewDto findById(@PathVariable Long id) {
        log.info("Looking for id {}", id);
        Review review = reviewRepository.findById(id).orElseThrow();
        return getReviewDto(review);
    }

    private ReviewDto getReviewDto(Review review) {
        return ReviewDto.builder()
                .movie(movieMapper.toDtoForReview(review.getMovie()))
                .id(review.getId())
                .review(review.getReview())
                .archived(review.isArchived())
                .author(AuthorDto.builder()
                        .id(review.getAuthor().getId())
                        .name(review.getAuthor().getName())
                        .build())
                .build();
    }
}
