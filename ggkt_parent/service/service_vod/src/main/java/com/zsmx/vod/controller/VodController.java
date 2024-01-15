package com.zsmx.vod.controller;

import com.zsmx.exception.GgktException;
import com.zsmx.result.Result;
import com.zsmx.vod.Utils.ConstantPropertiesUtil;
import com.zsmx.vod.Utils.Signature;
import com.zsmx.vod.service.VodService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * @author 钊思暮想
 * @date 2024/1/14 08:46
 */
@Tag(name = "腾讯云点播")
@RestController
@RequestMapping("/admin/vod")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;
    //返回客户端上传视频签名
    @GetMapping("sign")
    public Result sign(){
        Signature sign = new Signature();
        sign.setSecretId(ConstantPropertiesUtil.ACCESS_KEY_ID);
        sign.setSecretKey(ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        sign.setCurrentTime(System.currentTimeMillis()/1000);
        sign.setRandom(new Random().nextInt(Integer.MAX_VALUE));
        sign.setSignValidDuration(3600*24*2);
        try {
            String signature = sign.getUploadSignature();
            System.out.println("signature : " + signature);
            return Result.ok(signature);
        } catch (Exception e) {
            System.out.print("获取签名失败");
            e.printStackTrace();
            throw new GgktException(20001,"获取签名失败");
        }
    }

    //上传视频
    @PostMapping("upload")
    public Result upload(@Parameter(name = "file", description = "文件", required = true)
                         @RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        String originalFilename = file.getOriginalFilename();
        String videoId = vodService.uploadVideo(inputStream, originalFilename);
        return Result.ok(videoId);
    }
    //删除视频
    @DeleteMapping("remove/{ }")
    public Result removeVideo( @PathVariable String videoSourceId) {
        vodService.removeVideo(videoSourceId);
        return Result.ok();
    }
}
