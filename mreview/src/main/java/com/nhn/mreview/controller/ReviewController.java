package com.nhn.mreview.controller;

import com.nhn.mreview.dto.ReviewDTO;
import com.nhn.mreview.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("review")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("mno") Long mno) {
        log.info("------------list---------");
        log.info("mno: " + mno);

        List<ReviewDTO> reviewDTOList = reviewService.getListOfMoive(mno);

        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDTO reviewDTO) {
        log.info("---------------add MovieReview--------------");
        log.info("reviewDTO: " + reviewDTO);

        Long reviewnum = reviewService.register(reviewDTO);

        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewnum, @RequestBody ReviewDTO reviewDTO) {
        log.info("---------------modify MovieReview--------------");
        log.info("reviewDTO: " + reviewDTO);

        reviewService.modify(reviewDTO);

        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> removeReview(@PathVariable Long reviewnum) {
        log.info("---------------remove MovieReview--------------");
        log.info("reviewDTO: " + reviewnum);

        reviewService.remove(reviewnum);

        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }
}
