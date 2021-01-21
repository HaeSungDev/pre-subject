package com.nhn.mreview.service;

import com.nhn.mreview.dto.ReviewDTO;
import com.nhn.mreview.entity.Member;
import com.nhn.mreview.entity.Movie;
import com.nhn.mreview.entity.Review;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getListOfMoive(Long mno);

    Long register(ReviewDTO reviewDTO);

    void modify(ReviewDTO reviewDTO);

    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDTO reviewDTO) {
        return Review.builder()
            .reviewnum(reviewDTO.getReviewnum())
            .movie(Movie.builder().mno(reviewDTO.getMno()).build())
            .member(Member.builder().mid(reviewDTO.getMid()).build())
            .grade(reviewDTO.getGrade())
            .text(reviewDTO.getText())
            .build();
    }

    default ReviewDTO entityToDTO(Review review) {
        return ReviewDTO.builder()
            .reviewnum(review.getReviewnum())
            .mno(review.getMovie().getMno())
            .mid(review.getMember().getMid())
            .nickname(review.getMember().getNickname())
            .email(review.getMember().getEmail())
            .grade(review.getGrade())
            .text(review.getText())
            .regDate(review.getRegDate())
            .modDate(review.getModDate())
            .build();
    }
}
