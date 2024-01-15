package com.zsmx.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 钊思暮想
 * @date 2024/1/6 12:11
 */
public interface FileService {
    //文件上传
    String upload(MultipartFile file);
}
