package com.siriusbase.MusicPortalsAggregator.youtube;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.siriusbase.MusicPortalsAggregator.config.PrivateConfig;
import com.siriusbase.MusicPortalsAggregator.config.PublicConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class YoutubeService {
    private final PrivateConfig.Youtube privateConfig;
    private final PublicConfig.Youtube publicConfig;

    private static final String CLIENT_SECRET_PATH = "classpath:client_secret.json";
    private static final Collection<String> SCOPES = List.of("https://www.googleapis.com/auth/youtube.readonly",
            "https://www.googleapis.com/auth/youtube");

    private static final String APPLICATION_NAME = "API code samples";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public YoutubeService(PrivateConfig privateConfig, PublicConfig publicConfig) {
        this.privateConfig = privateConfig.getYoutube();
        this.publicConfig = publicConfig.getYoutube();
    }

    public void fetchMyPlaylists() throws IOException, GeneralSecurityException {
        YouTube youtubeService = getService();

        log.info(String.valueOf(getMyPlaylists(youtubeService)));
    }

    public GenericJson fetchMySpringVideos() throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();

        var json = getPlaylistItems(youtubeService, publicConfig.getSpringPlaylistId());
        log.info(String.valueOf(json));

        return json;
    }

    private GenericJson getMyPlaylists(YouTube youtubeService) throws IOException {
        YouTube.Playlists.List request =
                youtubeService.playlists().list("id,snippet,contentDetails,status")
                        .setPrettyPrint(true)
                        .setChannelId(privateConfig.getYoutubeChannelId());

        return request.execute();
    }

    private GenericJson getPlaylistItems(YouTube youtubeService, String playlistId) throws IOException {
        YouTube.PlaylistItems.List request = youtubeService.playlistItems().list("id,snippet,contentDetails,status").setMaxResults(50L).setPrettyPrint(true).setPlaylistId(playlistId);

        return request.execute();
    }

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    private YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = authorize(httpTransport);

        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
    }

    /**
     * Create an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    private Credential authorize(final NetHttpTransport httpTransport) throws IOException {
        InputStream in = new FileInputStream(ResourceUtils.getFile(CLIENT_SECRET_PATH));

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = getGoogleAuthorizationCodeFlow(httpTransport, clientSecrets);

        return getCredentialsAuthorizationCodeInstalledApp(flow);
    }

    private GoogleAuthorizationCodeFlow getGoogleAuthorizationCodeFlow(NetHttpTransport httpTransport, GoogleClientSecrets clientSecrets) {
        return new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES).build();
    }

    private Credential getCredentialsAuthorizationCodeInstalledApp(GoogleAuthorizationCodeFlow flow) throws IOException {
        AuthorizationCodeInstalledApp authorizationCodeInstalledApp = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver(), new AuthorizationCodeInstalledApp.DefaultBrowser());
        return authorizationCodeInstalledApp.authorize(privateConfig.getYoutubeUserId());
    }
}
