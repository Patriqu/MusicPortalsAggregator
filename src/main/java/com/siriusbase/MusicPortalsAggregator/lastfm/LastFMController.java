package com.siriusbase.MusicPortalsAggregator.lastfm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/lastfm")
public class LastFMController {
    LastFmService lastFmService;

    LastFMController(LastFmService lastFmService) {
        this.lastFmService = lastFmService;
    }

    @GetMapping("/list")
    List<String> fetchLastPlayed() {
        return lastFmService.placeholderLastPlayed();
    }


}
