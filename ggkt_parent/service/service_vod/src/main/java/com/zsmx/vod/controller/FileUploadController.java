package com.zsmx.vod.controller;

import com.zsmx.result.Result;
import com.zsmx.vod.service.FileService;
import io.prometheus.client.Summary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 钊思暮想
 * @date 2024/1/6 12:27
 */
@Tag(name="文件上传")
@RestController
@RequestMapping("/admin/vod/file")
@CrossOrigin
public class FileUploadController {
    @Autowired
    private FileService fileService;

    @Operation(summary = "文件上传")
    @PostMapping("upload")
    public Result upload(@Parameter MultipartFile file){
        String upload = fileService.upload(file);
        return Result.ok(upload).message("上传成功");
    }

    @GetMapping("/file")
    public ModelAndView fileUploadForm() {
        return new ModelAndView("fileUploadForm");
    }

    @PostMapping("/file")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // 获取上传文件的输入流
                InputStream inputStream = file.getInputStream();

                // 在这里可以使用 inputStream 处理文件流

                // 关闭流
                inputStream.close();

                return "File uploaded successfully!";
            } catch (IOException e) {
                return "Error uploading file: " + e.getMessage();
            }
        } else {
            return "Please select a file to upload.";
        }
    }
}
