package com.pinyougou.manager.controller;

import org.apache.commons.io.FilenameUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UploadController {

    @Value("${fileServerUrl}")
    private String fileServerUrl;

    @PostMapping("/upload")
    public Map<String,Object> upload(@RequestParam("file")MultipartFile multipartFile){
        Map<String, Object> datamap = new HashMap<>();
        datamap.put("status",500);
        try {
          String config_filename = this.getClass().getResource("/fastdfs_client.conf").getPath();
            ClientGlobal.init(config_filename);
            StorageClient client = new StorageClient();
            String originalFilename = multipartFile.getOriginalFilename();
            String[] StringFiles = client.upload_file(multipartFile.getBytes(), FilenameUtils.getExtension(originalFilename), null);

            StringBuilder url = new StringBuilder(fileServerUrl);
            for (String strfile : StringFiles) {
                url.append("/"+strfile);
            }
            datamap.put("status",200);
            datamap.put("url",url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datamap;
    }








}
