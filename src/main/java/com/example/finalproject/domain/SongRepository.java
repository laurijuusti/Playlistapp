package com.example.finalproject.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    // Example custom query methods, if needed:
    List<Song> findByArtist(String artist);

    List<Song> findByGenre(String genre);

    List<Song> findByReleaseYear(Integer releaseYear);

    List<Song> findByNameContaining(String keyword);
}
