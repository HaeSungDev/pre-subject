package com.nhn.mreview.service;

import com.nhn.mreview.dto.MovieDTO;
import com.nhn.mreview.dto.MovieImageDTO;
import com.nhn.mreview.entity.Movie;
import com.nhn.mreview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {
    Long register(MovieDTO movieDTO);

    default Map<String, Object> dtoToEntity(MovieDTO movieDTO) {
        Map<String, Object> entityMap = new HashMap<>();

        Movie movie = Movie.builder()
                .mno(movieDTO.getMno())
                .title(movieDTO.getTitle())
                .build();

        entityMap.put("movie", movie);

        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();

        if (imageDTOList != null && imageDTOList.size() > 0) {
            List<MovieImage> movieImageList = imageDTOList.stream()
                .map(movieImageDTO -> {
                    return MovieImage.builder()
                            .path(movieImageDTO.getPath())
                            .imgName(movieImageDTO.getImgName())
                            .uuid(movieImageDTO.getUuid())
                            .movie(movie)
                            .build();
                })
                .collect(Collectors.toList());

            entityMap.put("imgList", movieImageList);
        }

        return entityMap;
    }
}
