package com.siriusbase.MusicPortalsAggregator.youtube;

import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/youtube")
public class YoutubeController {
    YoutubeService youtubeService;

    public YoutubeController(YoutubeService youtubeService) {
        this.youtubeService = youtubeService;
    }

    @GetMapping("/playlist")
    void getPlaylists() throws GeneralSecurityException, IOException {
        youtubeService.fetchMyPlaylists();
    }

    @GetMapping("/spring-videos")
    String getSpringVideos(Model model) throws GeneralSecurityException, IOException {
        var videos = youtubeService.fetchMySpringVideos();

        List<String> titles = new ArrayList<>();
        List<PlaylistItem> items = ((PlaylistItemListResponse) videos).getItems();

        items.iterator().forEachRemaining
                (item -> titles.add(item.getSnippet().getTitle()));

        model.addAttribute("springTitlesYoutube", titles);

        return "index";
    }
}
