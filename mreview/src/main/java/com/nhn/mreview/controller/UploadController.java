package com.nhn.mreview.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@Log4j2
public class UploadController {
    @PostMapping("/uploadAjax")
    public void uploadFile(MultipartFile[] uploadFiles) {
        for (MultipartFile uploadFile : uploadFiles) {
            String originalName = uploadFile.getOriginalFilename();
            String filename = originalName.substring(originalName.lastIndexOf(File.pathSeparator) + 1);

            log.info("fileName: " + filename);
        }
    }
}
