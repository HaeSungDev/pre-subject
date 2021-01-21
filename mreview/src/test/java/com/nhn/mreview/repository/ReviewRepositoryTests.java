package com.nhn.mreview.repository;

import com.nhn.mreview.entity.Member;
import com.nhn.mreview.entity.Movie;
import com.nhn.mreview.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMovieReviews() {
        IntStream.rangeClosed(1, 200).forEach(i -> {
            Long mno = (long)(Math.random() * 100) + 1;

            Long mid = (long)(Math.random() * 100) + 1;

            Member member = Member.builder().mid(mid).build();
            Movie movie = Movie.builder().mno(mno).build();

            Review review = Review.builder()
                    .member(member)
                    .movie(movie)
                    .grade((int)(Math.random() * 5) + 1)
                    .text("이 영화에 대한 느낌..." + i)
                    .build();

            reviewRepository.save(review);
        });
    }
}
