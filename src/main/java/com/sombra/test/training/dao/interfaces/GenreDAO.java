package com.sombra.test.training.dao.interfaces;

import com.sombra.test.training.entities.Genre;

import java.util.List;

/**
 * Created by Artem on 08.09.2016.
 */
public interface GenreDAO {

    List<Genre> getGenres();
}
