package com.example.rest2.api.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {

    /**
     * uses to upload a single file
     * @param file request form data form client
     * @return FileDto
     */

    FileDto uploadSingle(MultipartFile file);

    /**
     * uses to upload a multiple files
     * @param files request form data form client
     * @return List<FileDto>
     */
    List<FileDto> uploadMultiple(List<MultipartFile> files);

    /**
     * uses to find all file
     * @return List<FileDto>
     */
    List<FileDto> findAll();

    /**
     * user to find fill by name
     * @param name
     * @return FileDto
     */
    FileDto findFileByName(String name);

    /**
     * use to delete file by name
     * @param name
     * @return String
     */
    String deleteFile(String name);

    /**
     * uses to delete all file
     * @return String
     */
    String  deleteAllFile();

    /**
     * user to download file Url
     * @param fileName
     * @return Resource
     */
    Resource downloadFileName(String fileName);
}
