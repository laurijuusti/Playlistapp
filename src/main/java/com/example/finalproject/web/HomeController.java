package com.example.finalproject.web;

import com.example.finalproject.domain.Song;
import com.example.finalproject.domain.SongRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
// Tämä controller hoitaa index ja songs-sivut
public class HomeController {

    private final SongRepository songRepository;

    public HomeController(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/songs")
    @Cacheable("songs") // Taas välimuistiin tiedon laittamista
    public String showAllSongs(Model model) {
        List<Song> songs = songRepository.findAll();
        model.addAttribute("songs", songs);
        return "songs";
    }

    @PostMapping("/songs/add") // Kappaleen lisäys, vaatii kaikki parametrit tuolta tyhjästä Song-luokasta
    // Songs.html formi tuo tähän POST-requestin alla olevilla parametreillä
    public String addSong(@RequestParam String name, @RequestParam String artist, @RequestParam String genre,
            @RequestParam String album, @RequestParam String duration, @RequestParam Integer releaseYear) {
        Song newSong = new Song();
        newSong.setName(name);
        newSong.setArtist(artist);
        newSong.setAlbum(album);
        newSong.setDuration(duration);
        newSong.setReleaseYear(releaseYear);
        newSong.setGenre(genre);
        songRepository.save(newSong);
        return "redirect:/songs";
    }

}
