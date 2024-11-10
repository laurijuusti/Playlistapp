package com.example.finalproject.web;

import com.example.finalproject.domain.Song;
import com.example.finalproject.domain.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongRepository songRepository;

    @Autowired
    public SongController(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @GetMapping("/all")
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id) {
        Optional<Song> song = songRepository.findById(id);
        return song.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Song> createSong(@RequestBody Song song) {
        Song createdSong = songRepository.save(song);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSong);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable Long id, @RequestBody Song updatedSong) {
        return songRepository.findById(id)
                .map(existingSong -> {
                    existingSong.setName(updatedSong.getName());
                    existingSong.setArtist(updatedSong.getArtist());
                    existingSong.setAlbum(updatedSong.getAlbum());
                    existingSong.setGenre(updatedSong.getGenre());
                    existingSong.setReleaseYear(updatedSong.getReleaseYear());
                    existingSong.setDuration(updatedSong.getDuration());
                    existingSong.setLanguage(updatedSong.getLanguage());
                    existingSong.setComposer(updatedSong.getComposer());
                    existingSong.setLyricsUrl(updatedSong.getLyricsUrl());
                    Song savedSong = songRepository.save(existingSong);
                    return ResponseEntity.ok(savedSong);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        if (songRepository.existsById(id)) {
            songRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
