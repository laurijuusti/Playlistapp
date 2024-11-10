package com.example.finalproject.web;

import com.example.finalproject.domain.Playlist;
import com.example.finalproject.domain.Song;
import com.example.finalproject.domain.SongRepository;
import com.example.finalproject.domain.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final SongRepository songRepository;

    @Autowired
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
    public String showAllSongs(Model model) {
        List<Song> songs = songRepository.findAll();
        model.addAttribute("songs", songs);
        return "songs";
    }

    @GetMapping("/songs/{id}")
    public String showSongById(@PathVariable Long id, Model model) {
        Optional<Song> song = songRepository.findById(id);
        if (song.isPresent()) {
            model.addAttribute("song", song.get());
            return "songDetails";
        } else {
            return "redirect:/songs";
        }
    }

}
