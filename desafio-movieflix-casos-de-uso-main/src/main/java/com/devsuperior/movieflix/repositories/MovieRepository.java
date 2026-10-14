package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    /*@Query("SELECT m FROM Movie m JOIN FETCH m.genre WHERE m.genre.id = :genreId ORDER BY m.title ASC")
    Page<Movie> findByGenreId(@Param("genreId") Long genreId, Pageable pageable);*/

    @Query("SELECT m FROM Movie m JOIN FETCH m.genre")
    Page<Movie> findAllPaged(Pageable pageable);

    @Query(
            value = "SELECT m FROM Movie m JOIN FETCH m.genre WHERE m.genre.id = :genreId",
            countQuery = "SELECT COUNT(m) FROM Movie m WHERE m.genre.id = :genreId"
    )
    Page<Movie> findByGenreId(@Param("genreId") Long genreId, Pageable pageable);
}