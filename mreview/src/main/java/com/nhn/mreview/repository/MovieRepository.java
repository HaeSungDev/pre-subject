package com.nhn.mreview.repository;

import com.nhn.mreview.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(
        "select m, mi, avg(coalesce(r.grade, 0)), count(distinct r) " +
            "from Movie m " +
            "left outer join MovieImage mi on mi.movie = m " +
            "left outer join Review r on r.movie = m group by m"
    )
    Page<Object[]> getListPage(Pageable pageable);

    @Query(
        "select m, mi, avg(coalesce(r.grade, 0)), count(distinct r) " +
            "from Movie m " +
            "left outer join MovieImage mi on mi.movie = m " +
            "left outer join Review r on r.movie = m " +
            "where m.mno = :mno group by m"
    )
    List<Object[]> getMovieWithAll(Long mno);
}
