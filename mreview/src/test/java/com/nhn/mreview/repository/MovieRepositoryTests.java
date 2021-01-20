package com.nhn.mreview.repository;

import com.nhn.mreview.entity.Movie;
import com.nhn.mreview.entity.MovieImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class MovieRepositoryTests {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieImageRepository movieImageRepository;

    @Commit
    @Transactional
    @Test
    public void insertMovies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Movie movie = Movie.builder().title("Movie...." + i).build();

            System.out.println("---------------------------------------------");

            movieRepository.save(movie);

            int count = (int)(Math.random() * 5) + 1;

            for (int j = 0; j < count; j++) {
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("test" + j + ".jpg")
                        .build();

                movieImageRepository.save(movieImage);
            }

            System.out.println("==============================================");
        });
    }
}
