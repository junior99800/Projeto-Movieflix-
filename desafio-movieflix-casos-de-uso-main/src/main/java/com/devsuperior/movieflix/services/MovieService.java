package com.devsuperior.movieflix.services;


import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Transactional(readOnly = true)
    public MovieDetailsDTO findById(Long id){
        Movie movie = movieRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new MovieDetailsDTO(movie);
    }

  /* @Transactional(readOnly = true)
    public Page<MovieCardDTO> findByGenre(Long genreId, Pageable pageable) {
        Page<Movie> page  = movieRepository.findByGenreId(genreId, pageable);
        return page.map(x -> new MovieCardDTO(x));
    }*/

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> findByGenre(Long genreId, Pageable pageable) {
        Page<Movie> page = (genreId == null)
                ? movieRepository.findAllPaged(pageable)
                : movieRepository.findByGenreId(genreId, pageable);
        return page.map(MovieCardDTO::new);
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> findReviews(Long movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new ResourceNotFoundException("Filme não encontrado");
        }
        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        return reviews.stream().map(ReviewDTO::new).toList();
    }
}
