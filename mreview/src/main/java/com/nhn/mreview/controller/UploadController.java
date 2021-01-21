package com.nhn.mreview.controller;

import com.nhn.mreview.dto.UploadResultDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {
    @Value("${com.nhn.upload.path}")
    private String uploadPath;

    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles) {
        List<UploadResultDTO> uploadResultDTOList = new ArrayList<>();

        for (MultipartFile uploadFile : uploadFiles) {
            if (!uploadFile.getContentType().startsWith("image")) {
                log.warn("this file is not image type");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            String originalName = uploadFile.getOriginalFilename();
            String filename = originalName.substring(originalName.lastIndexOf(File.pathSeparator) + 1);

            log.info("fileName: " + filename);

            String uuid = UUID.randomUUID().toString();

            String folderPath = makeFolder();

            Path savePath = Paths.get(uploadPath, folderPath, uuid + "_" + filename);

            try {
                uploadFile.transferTo(savePath);
                uploadResultDTOList.add(new UploadResultDTO(filename, uuid, folderPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<>(uploadResultDTOList, HttpStatus.OK);
    }

    public String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        log.info(str);

        String folderPath = str.replace("//", File.separator);

        File uploadPathFolder = new File(uploadPath, folderPath);

        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }

        return folderPath;
    }
}
