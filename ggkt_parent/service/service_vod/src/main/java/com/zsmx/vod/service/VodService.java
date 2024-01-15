package com.zsmx.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsmx.model.vod.Video;
import com.zsmx.vod.service.impl.VodServiceImpl;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @author 钊思暮想
 * @date 2024/1/14 08:47
 */
public interface VodService{
    //上传视频
    String uploadVideo(InputStream inputStream, String originalFilename);
    //删除视频
    void removeVideo(String videoSourceId);
}
