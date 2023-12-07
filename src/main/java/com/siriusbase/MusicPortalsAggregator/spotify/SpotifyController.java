package com.siriusbase.MusicPortalsAggregator.spotify;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/spotify")
public class SpotifyController {
    SpotifyService spotifyService;

    SpotifyController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping(value = "/artist", produces = MediaType.TEXT_HTML_VALUE)
    String fetchArtist() {
        return spotifyService.fetchArtistData();
    }

    @GetMapping(value = "/played", produces = MediaType.TEXT_HTML_VALUE)
    String fetchLastPlayed() {
        return spotifyService.fetchLastPlayed();
    }
}
