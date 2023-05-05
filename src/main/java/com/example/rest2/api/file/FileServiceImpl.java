package com.example.rest2.api.file;

import com.example.rest2.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.server-path}")
    private String fileServerPath;
    @Value("${file.base-url}")
    private String fileBaseUrl;

    private FileUtil fileUtil;

    @Autowired
    public void setFileUtil(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Override
    public FileDto uploadSingle(MultipartFile file) {
        return fileUtil.upload(file);


    }

    @Override
    public List<FileDto> uploadMultiple(List<MultipartFile> files) {
        List<FileDto> filesDto = new ArrayList<>();
        for (MultipartFile file : files) {
            filesDto.add(fileUtil.upload(file));
        }
        return filesDto;
    }

    @Override
    public List<FileDto> findAll() {
        File file = new File(fileServerPath);
        File[] files = file.listFiles();
        List<FileDto> fileDtoList = new ArrayList<>();
        assert files != null;
        for (File file1 : files) {
            fileDtoList
                    .add(
                            new FileDto(
                                    file1.getName()
                                    , fileBaseUrl + file1.getName()
                                    , file1.getName().substring(file1.getName().lastIndexOf(".") + 1)
                                    , file1.length()
                            )
                    );
        }
        return fileDtoList;
    }

    @Override
    public FileDto findFileByName(String name) {
        Path path;
        File file = new File(fileServerPath + name);
        try {
            path = Paths.get(fileServerPath + name + ".jpg");
            Resource resource = new UrlResource(path.toUri());
            try {
                if(resource.exists()){
                    return FileDto
                            .builder()
                            .name(name)
                            .size(resource.contentLength())
                            .url(fileBaseUrl+name + ".jpg")
                            .extension("jpg")
                            .build();
                }
                else{
                    path = Paths.get(fileServerPath + name + ".png");
                    resource=new UrlResource(path.toUri());
                    return FileDto
                            .builder()
                            .name(name)
                            .size(resource.contentLength())
                            .url(fileBaseUrl+name + ".png")
                            .extension("png")
                            .build();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteFile(String name) {
        Path path = Paths.get(fileServerPath+name);
        File file = new File(fileServerPath+name);
        try {
            path = Paths.get(fileServerPath + name + ".jpg");

            try {
                if (Files.exists(path)) {
                    Files.delete(path);


                } else {
                    path = Paths.get(fileServerPath + name + ".png");
                    Files.delete(path);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }catch (Exception e){

        }
        return "File is deleted Successfully";

    }
    @Override
    public String deleteAllFile() {
        File file = new File(fileServerPath);
        File[] files = file.listFiles();
        for (File file1 : files) {
            file1.delete();
        }
        return "DeleteAllFile is Successfully";
    }
}
