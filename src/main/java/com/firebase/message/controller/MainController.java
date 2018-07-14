package com.firebase.message.controller;

import com.firebase.message.FileStorageService;
import com.firebase.message.InstanceRepository;
import com.firebase.message.model.MessageSubmission;
import com.firebase.message.model.UploadFileResponse;
import com.firebase.message.repository.InstanceDto;
import com.firebase.message.service.Firebase;
import com.google.common.net.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Created by moizali on 6/20/18.
 */

@Slf4j
@RestController
@RequestMapping(path = "image/", produces = APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainController {

    private final Firebase firebase;
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private InstanceRepository instanceRepository;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/image/")
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        instanceRepository.findAll().forEach(instanceDto -> {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("fileDownloadUrl", fileDownloadUri);
            hashMap.put("fileName", fileName);
            firebase.sendMessage(new MessageSubmission("Hey!", "Someone is at your door", hashMap, instanceDto), new Firebase.TokenValiditiyInterface() {
                @Override
                public void onTokenValid() {

                }

                @Override
                public void onTokenInvalid(InstanceDto token) {
                    instanceRepository.delete(token);
                }
            });
        });
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
