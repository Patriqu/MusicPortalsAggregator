# Music Portals Aggregator

Spring Boot 3 web application in development. 

Goal of this application is to load list of videos and playlists from your YouTube profile, list your playlists and 
last 
played from your Spotify profile, and list last played from your Last.fm profile. The app should display them all on the page in a simple UI.
## How to run

#### Properties YML files
By default, only **_application-<PROFILE_NAME>.yml_** file with public properties is provided. You need to add 
**_application-private-<PROFILE_NAME>.yml_** file with your credentials to the **_config_** directory. Replace 
**_PROFILE_NAME_** with dev or prod. All properties are defined in **_PrivateConfig.java_**. These are properties to 
add:

```yml
spotify:
  spotifyRedirectUri: ""
  spotifyClientId: ""
  spotifyClientSecret: ""
youtube:
  youtubeApiKey: ""
  youtubeClientId: ""
  youtubeClientSecret: ""
  youtubeChannelId: ""
  youtubeUserId: ""
```
For Google API credentials, you also need to provide _client_secret.json_ to _resources_ directory.

#### Run application
In your IDE (Intellij, VS Code, CLI), to run the app, add _"dev"_ or _"prod"_ as active profile/command line 
  attribute.

Actually, fetching YouTube playlists is working correctly.

