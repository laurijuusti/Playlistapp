package com.example.finalproject.web;

import com.example.finalproject.domain.Playlist;
import com.example.finalproject.domain.PlaylistRepository;
import com.example.finalproject.domain.SongRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/playlists")
// Tämä controller on vain playlistejä varten
public class PlaylistController {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    public PlaylistController(PlaylistRepository playlistRepository, SongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }

    @GetMapping
    @Cacheable("playlists") // Soittolistojen tallennus selaimen välimuistiin, jotta tietokantaa joutuu
                            // käyttämään vähemmän
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

    @GetMapping("/{id}")
    public String showPlaylistDetails(@PathVariable Long id, Model model) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        model.addAttribute("playlist", playlist);
        model.addAttribute("songs", playlist.getSongs());
        return "playlist-details";
    }

}
