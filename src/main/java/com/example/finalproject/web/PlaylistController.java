package com.example.finalproject.web;

import com.example.finalproject.domain.Playlist;
import com.example.finalproject.domain.PlaylistRepository;
import com.example.finalproject.domain.Song;
import com.example.finalproject.domain.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    @Autowired
    public PlaylistController(PlaylistRepository playlistRepository, SongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }

    @GetMapping
    public String listPlaylists(Model model) {
        model.addAttribute("playlists", playlistRepository.findAll());
        model.addAttribute("playlist", new Playlist());
        return "playlists";
    }

    @PostMapping("/create")
    public String createPlaylist(@ModelAttribute Playlist playlist) {
        playlistRepository.save(playlist);
        return "redirect:/playlists";
    }

}
