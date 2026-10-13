package com.devsuperior.movieflix.services;


import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreServices {

    @Autowired
    private GenreRepository genreRepository;

    /*@Transactional(readOnly = true)
    public Page<GenreDTO> findAllPaged(Pageable pageable) {
        Page<Genre> list = genreRepository.findAll(pageable);
        return list.map(x -> new GenreDTO(x));
    }*/

    @Transactional(readOnly = true)
    public List<GenreDTO> findAll() {
        List<Genre> list = genreRepository.findAll();
        return list.stream()
                .map(GenreDTO::new)
                .toList();
    }

}
