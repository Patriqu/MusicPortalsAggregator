package com.siriusbase.MusicPortalsAggregator.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Setter
@Getter
@Configuration
@PropertySource("classpath:/config/application-private-dev.yml")
@ConfigurationProperties()
public class PrivateConfig {
    private Spotify spotify;
    private Youtube youtube;

    @Setter
    @Getter
    @Configuration
    @ConfigurationProperties()
    public static class Spotify {
        private String spotifyRedirectUri;
        private String spotifyClientId;
        private String spotifyClientSecret;
    }

    @Setter
    @Getter
    @Configuration
    @ConfigurationProperties()
    public static class Youtube {
        private String youtubeApiKey;
        private String youtubeClientId;
        private String youtubeClientSecret;
        private String youtubeChannelId;
        private String youtubeUserId;
    }
}