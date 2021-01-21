package com.nhn.mreview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Paths;

@Data
@AllArgsConstructor
public class UploadResultDTO implements Serializable {
    private String filename;
    private String uuid;
    private String folderPath;

    public String getImageURL() {
        try {
            return URLEncoder.encode(
                Paths.get(folderPath, uuid + "_" + filename).toString(), "UTF-8"
            );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getThumbnailURL() {
        try {
            return URLEncoder.encode(
                    Paths.get(folderPath, "s_" + uuid + "_" + filename).toString(), "UTF-8"
            );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
