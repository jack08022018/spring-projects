package com.jpa.service;

import com.google.auth.Credentials;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class GoogleDriveServiceConfig {
    private final GoogleCredentials googleCredentials;

    public GoogleDriveServiceConfig() throws IOException {
        // Load the credentials file (replace 'path/to/your/credentials.json' with your actual file path)
        InputStream credentialsStream = getClass().getResourceAsStream("/path/to/your/credentials.json");
        this.googleCredentials = ServiceAccountCredentials.fromStream(credentialsStream);
    }

    public GoogleCredentials getGoogleCredentials() {
        return this.googleCredentials;
    }

    // You can add other configuration methods as needed
}
