package com.nhn.mreview.service;

import com.nhn.mreview.dto.ReviewDTO;
import com.nhn.mreview.entity.Movie;
import com.nhn.mreview.entity.Review;
import com.nhn.mreview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDTO> getListOfMoive(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();

        List<Review> result = reviewRepository.findByMovie(movie);

        return result.stream()
            .map(this::entityToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO reviewDTO) {
        Review review = dtoToEntity(reviewDTO);

        reviewRepository.save(review);

        return review.getReviewnum();
    }

    @Override
    public void modify(ReviewDTO reviewDTO) {
        Optional<Review> result = reviewRepository.findById(reviewDTO.getReviewnum());

        if (result.isPresent()) {
            Review review = result.get();

            review.changeGrade(reviewDTO.getGrade());
            review.changeText(reviewDTO.getText());

            reviewRepository.save(review);
        }
    }

    @Override
    public void remove(Long reviewnum) {
        reviewRepository.deleteById(reviewnum);
    }
}
