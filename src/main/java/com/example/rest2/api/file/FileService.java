package com.example.rest2.api.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {

    FileDto uploadSingle(MultipartFile file);
    List<FileDto> uploadMultiple(List<MultipartFile> files);

    List<FileDto> findAll();

    FileDto findFileByName(String name);
    String deleteFile(String name);

    String  deleteAllFile();
}
