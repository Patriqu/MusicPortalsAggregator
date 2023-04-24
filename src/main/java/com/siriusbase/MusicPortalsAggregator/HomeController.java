package com.siriusbase.MusicPortalsAggregator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("event", "Music Portals Aggregator - Homepage");

        return "index";
    }

    @GetMapping("/callback")
    public String callback(Model model) {
        model.addAttribute("event", "Callback from Spotify authorization");

        return "index";
    }
}
