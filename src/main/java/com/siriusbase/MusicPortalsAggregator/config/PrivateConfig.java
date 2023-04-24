package com.siriusbase.MusicPortalsAggregator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/config/application-private-dev.yml")
@ConfigurationProperties()
public class PrivateConfig {
    private Spotify spotify;
    private Youtube youtube;

    public Spotify getSpotify() {
        return spotify;
    }

    public void setSpotify(Spotify spotify) {
        this.spotify = spotify;
    }

    public Youtube getYoutube() {
        return youtube;
    }

    public void setYoutube(Youtube youtube) {
        this.youtube = youtube;
    }


    @Configuration
    @ConfigurationProperties()
    public static class Spotify {
        private String spotifyRedirectUri;
        private String spotifyClientId;
        private String spotifyClientSecret;

        public String getSpotifyRedirectUri() {
            return spotifyRedirectUri;
        }

        public void setSpotifyRedirectUri(String spotifyRedirectUri) {
            this.spotifyRedirectUri = spotifyRedirectUri;
        }

        public String getSpotifyClientId() {
            return spotifyClientId;
        }

        public void setSpotifyClientId(String spotifyClientId) {
            this.spotifyClientId = spotifyClientId;
        }

        public String getSpotifyClientSecret() {
            return spotifyClientSecret;
        }

        public void setSpotifyClientSecret(String spotifyClientSecret) {
            this.spotifyClientSecret = spotifyClientSecret;
        }
    }

    @Configuration
    @ConfigurationProperties()
    public static class Youtube {
        private String youtubeApiKey;
        private String youtubeClientId;
        private String youtubeClientSecret;
        private String youtubeChannelId;
        private String youtubeUserId;

        public String getYoutubeApiKey() {
            return youtubeApiKey;
        }

        public void setYoutubeApiKey(String youtubeApiKey) {
            this.youtubeApiKey = youtubeApiKey;
        }

        public String getYoutubeClientId() {
            return youtubeClientId;
        }

        public void setYoutubeClientId(String youtubeClientId) {
            this.youtubeClientId = youtubeClientId;
        }

        public String getYoutubeClientSecret() {
            return youtubeClientSecret;
        }

        public void setYoutubeClientSecret(String youtubeClientSecret) {
            this.youtubeClientSecret = youtubeClientSecret;
        }

        public String getYoutubeChannelId() {
            return youtubeChannelId;
        }

        public void setYoutubeChannelId(String youtubeChannelId) {
            this.youtubeChannelId = youtubeChannelId;
        }

        public String getYoutubeUserId() {
            return youtubeUserId;
        }

        public void setYoutubeUserId(String youtubeUserId) {
            this.youtubeUserId = youtubeUserId;
        }
    }
}