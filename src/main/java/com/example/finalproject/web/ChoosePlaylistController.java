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
@RequestMapping("/choose-playlist")
public class ChoosePlaylistController {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    @Autowired
    public ChoosePlaylistController(PlaylistRepository playlistRepository, SongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }

    @GetMapping("/{songId}")
    public String showPlaylists(@PathVariable Long songId, Model model) {
        model.addAttribute("playlists", playlistRepository.findAll());
        model.addAttribute("songId", songId);
        return "choose-playlist";
    }

    @PostMapping("/{songId}")
    public String addSongToPlaylist(@PathVariable Long songId, @RequestParam Long playlistId) {
        Song song = songRepository.findById(songId).orElseThrow(() -> new RuntimeException("Song not found"));
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        playlist.getSongs().add(song);
        playlistRepository.save(playlist);

        return "redirect:/songs"; // Redirect to the songs list or any appropriate page
    }
}
