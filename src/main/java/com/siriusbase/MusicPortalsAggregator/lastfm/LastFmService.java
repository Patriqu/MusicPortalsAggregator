package com.siriusbase.MusicPortalsAggregator.lastfm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LastFmService {
    List<String> placeholderLastPlayed() {
        return List.of("The Prodigy - Fire (Sunrise Version)", "Depeche Mode - Enjoy the Silence",
                "Eurythmics - Sweet Dreams (Are Made Of This)");
    }
}
