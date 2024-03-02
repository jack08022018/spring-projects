package com.jpa.service;

import com.fasterxml.jackson.core.util.JacksonFeature;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleDriveServiceImpl implements GoogleDriveService {
//    private final Drive drive;

    @Autowired
    public GoogleDriveServiceImpl(GoogleDriveServiceConfig googleDriveServiceConfig) {
//        this.drive = new Drive.Builder(
//                new NetHttpTransport(),
//                new JacksonFeature(),
//                new HttpCredentialsAdapter(googleDriveServiceConfig.getGoogleCredentials()))
//                .setApplicationName("YourAppName")
//                .build();
    }

    @Override
    public List<File> getAllFilesInfo() {
        return null;
    }
}
