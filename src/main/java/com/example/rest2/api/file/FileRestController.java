package com.example.rest2.api.file;

import com.example.rest2.base.BaseRest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@Slf4j
@RequiredArgsConstructor
public class FileRestController {
    private final FileService fileService;

    @PostMapping
    public BaseRest<?> uploadSingle(@RequestPart MultipartFile file) {
        log.info("file Request ={} ",file);
        FileDto fileDto = fileService.uploadSingle(file);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has been Upload")
                .timestamp(LocalDateTime.now())
                .date(fileDto)
                .build();
    }
    @PostMapping("/multiple")
    public BaseRest<?> uploadMultiple(@RequestPart List<MultipartFile> files){
        log.info("file Request ={} ",files);
        List<FileDto> fileDto = fileService.uploadMultiple(files);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has been Upload")
                .timestamp(LocalDateTime.now())
                .date(fileDto)
                .build();
    }

    @GetMapping
    public BaseRest<?> findAll(){
        List<FileDto> fileDto = fileService.findAll();
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has been Upload")
                .timestamp(LocalDateTime.now())
                .date(fileDto)
                .build();
    }
    @GetMapping("/{name}")
    public BaseRest<?> findFileByName(@PathVariable String name){
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has been Upload")
                .timestamp(LocalDateTime.now())
                .date(fileService.findFileByName(name))
                .build();
    }

    @DeleteMapping("/delete/{name}")
    public BaseRest<?> deleteFile(@PathVariable String name){
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has delete")
                .timestamp(LocalDateTime.now())
                .date(fileService.deleteFile(name))
                .build();
    }
    @DeleteMapping("/deletes")
    public BaseRest<?> deleteAllFile(){
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File has delete")
                .timestamp(LocalDateTime.now())
                .date(fileService.deleteAllFile())
                .build();
    }

}
