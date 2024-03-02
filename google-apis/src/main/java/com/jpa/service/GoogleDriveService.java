package com.jpa.service;

import com.google.api.services.drive.model.File;

import java.util.List;

public interface GoogleDriveService {
    List<File> getAllFilesInfo();
}
