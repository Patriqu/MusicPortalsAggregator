package com.siriusbase.MusicPortalsAggregator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/config/application-${spring.profiles.active}.yml")
@ConfigurationProperties()
public class PublicConfig {
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
        @Value("${spotify.authorizeUri}")
        private String authorizeUri;

        public String getAuthorizeUri() {
            return authorizeUri;
        }

        public void setAuthorizeUri(String authorizeUri) {
            this.authorizeUri = authorizeUri;
        }
    }
    @Configuration
    @ConfigurationProperties()
    public static class Youtube {
        @Value("${youtube.springPlaylistId}")
        private String springPlaylistId;

        public String getSpringPlaylistId() {
            return springPlaylistId;
        }

        public void setSpringPlaylistId(String springPlaylistId) {
            this.springPlaylistId = springPlaylistId;
        }
    }
}