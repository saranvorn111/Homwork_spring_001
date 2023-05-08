package com.example.rest2.api.file;
import com.example.rest2.base.BaseRest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@Slf4j
@RequiredArgsConstructor
public class FileRestController {
    private final FileService fileService;
    //uploadSingle file
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
    //UploadMultiple files
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

    //Find all files
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

    //Find fill by name
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


    //Delete file by name
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

    //Delete All file
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


    //Download file Name
    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadFileName(@PathVariable("fileName") String fileName){
        Resource resource = fileService.downloadFileName(fileName);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+resource.getFilename()+"\"")
                .body(resource);
    }
}
