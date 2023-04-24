package com.siriusbase.MusicPortalsAggregator.spotify;

import com.siriusbase.MusicPortalsAggregator.config.PrivateConfig;
import com.siriusbase.MusicPortalsAggregator.config.PublicConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@Slf4j
@Service
public class SpotifyService {
    private final PrivateConfig.Spotify privateConfig;
    private final PublicConfig.Spotify publicConfig;

    private String accessToken;

    private static final String SCOPE = "user-read-private,user-read-email,user-read-recently-played";

    private final Random random = new Random();

    public SpotifyService(PrivateConfig.Spotify privateConfig, PublicConfig publicConfig) {
        this.privateConfig = privateConfig;
        this.publicConfig = publicConfig.getSpotify();
    }

    String fetchArtistData() {
        HttpClient httpClient = getHttpClient();
        authorizeClientCredentials(httpClient);

        var response = doRequest(requestArtistData(), httpClient);
        return response != null ? response.body() : "";
    }

    String fetchLastPlayed() {
        HttpClient httpClient = getHttpClient();
        HttpRequest request = authorizeCodeFlow();

        // todo: add requestLastPlayed() call

        var response = doRequest(request, httpClient);
        return response != null ? response.body() : "";
    }

    private HttpClient getHttpClient() {
        return HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
    }

    private void authorizeClientCredentials(HttpClient httpClient) {
        var request = requestAccessToken();

        var response = doRequest(request, httpClient);
        if (response != null) {
            try {
                accessToken = new JSONObject(response.body()).getString("access_token");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private HttpRequest requestAccessToken() {
        var url = "https://accounts.spotify.com/api/token"
                + "?grant_type=client_credentials"
                + "&client_id=" + URLEncoder.encode(privateConfig.getSpotifyClientId(), StandardCharsets.UTF_8)
                + "&client_secret=" + URLEncoder.encode(privateConfig.getSpotifyClientSecret(), StandardCharsets.UTF_8);

        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .headers("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
    }

    private HttpResponse<String> doRequest(HttpRequest request, HttpClient httpClient) {
        HttpResponse<String> response = null;

        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            log.info("Status code: " + response.statusCode());
            log.info("Response body: " + response.body());
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
        }

        return response;
    }

    private HttpRequest requestArtistData() {
        var url = "https://api.spotify.com/v1/artists/4Z8W4fKeB5YxbusRsdQVPb";

        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .headers("Authorization", "Bearer " + accessToken)
                .GET()
                .build();
    }

    private HttpRequest requestLastPlayed() {
        var url = "https://api.spotify.com/v1/me/player/recently-played"
                + "?limit=10";

        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .headers("Authorization", "Bearer " + accessToken)
                .GET()
                .build();
    }

    private HttpRequest authorizeCodeFlow() {
        var state = generateRandomString();

        var url = publicConfig.getAuthorizeUri()
                + "?response_type=" + URLEncoder.encode("code", StandardCharsets.UTF_8)
                + "&client_id=" + URLEncoder.encode(privateConfig.getSpotifyClientId(), StandardCharsets.UTF_8)
                + "&scope=" + URLEncoder.encode(SCOPE, StandardCharsets.UTF_8)
                + "&redirect_uri=" + URLEncoder.encode(privateConfig.getSpotifyRedirectUri(), StandardCharsets.UTF_8)
                + "&state=" + URLEncoder.encode(state, StandardCharsets.UTF_8);

        log.info("url=" + url);

        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
    }

    private String generateRandomString() {
        byte[] array = new byte[16];
        random.nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    @Deprecated
    private HttpRequest authorizeImplicitGrantFlow(String state) {
        var url = publicConfig.getAuthorizeUri()
                + "?response_type=token"
                + "&client_id=" + URLEncoder.encode(privateConfig.getSpotifyClientId(), StandardCharsets.UTF_8)
                + "&scope=" + URLEncoder.encode(SCOPE, StandardCharsets.UTF_8)
                + "&redirect_uri=" + URLEncoder.encode(privateConfig.getSpotifyRedirectUri(), StandardCharsets.UTF_8)
                + "&state=" + URLEncoder.encode(state, StandardCharsets.UTF_8);

        log.info("url=" + url);

        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
    }
}
