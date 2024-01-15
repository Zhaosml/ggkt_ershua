package com.zsmx.vod.service.impl;

import com.qcloud.vod.VodUploadClient;
import com.qcloud.vod.model.VodUploadRequest;
import com.qcloud.vod.model.VodUploadResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.vod.v20180717.VodClient;
import com.tencentcloudapi.vod.v20180717.models.DeleteMediaRequest;
import com.tencentcloudapi.vod.v20180717.models.DeleteMediaResponse;
import com.zsmx.exception.GgktException;
import com.zsmx.vod.Utils.ConstantPropertiesUtil;
import com.zsmx.vod.service.VodService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

/**
 * @author 钊思暮想
 * @date 2024/1/14 08:47
 */
@Service
public class VodServiceImpl implements VodService {
    //上传视频，只能指定路径上传     不推荐使用
    @Override
    public String uploadVideo(InputStream inputStream, String originalFilename) {
        try {
            //指定当前腾讯云账号id和key  使用云 API 密钥初始化 VodUploadClient 实例。
            VodUploadClient client =
                    new VodUploadClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                                        ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //设置媒体本地上传路径。
            VodUploadRequest request = new VodUploadRequest();
            //视频本地地址
            request.setMediaFilePath("/Users/ikun/Movies/a4ozjrp_460sv.mp4");
            //指定任务流     自适应转码
            request.setProcedure("LongVideoPreset");
            //调用上传方法，传入接入点地域及上传请求。
            VodUploadResponse response = client.upload("ap-guangzhou", request);
            //返回文件id保存到业务表，用于控制视频播放
            String fileId = response.getFileId();
            System.out.println("Upload FileId = "+response.getFileId());
            return fileId;
        }
        catch (Exception e){
            throw new GgktException(20001,"上传视频失败");
        }
    }
    //删除视频
    @Override
    public void removeVideo(String videoSourceId) {
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户 SecretId 和 SecretKey，此处还需注意密钥对的保密
            // 代码泄露可能会导致 SecretId 和 SecretKey 泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议采用更安全的方式来使用密钥，请参见：https://cloud.tencent.com/document/product/1278/85305
            // 密钥可前往官网控制台 https://console.cloud.tencent.com/cam/capi 进行获取
            Credential cred = new Credential
                    (ConstantPropertiesUtil.ACCESS_KEY_ID ,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            // 实例化要请求产品的client对象,clientProfile是可选的
            VodClient client = new VodClient(cred, "");
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DeleteMediaRequest req = new DeleteMediaRequest();
            req.setFileId(videoSourceId);
            // 返回的resp是一个DeleteMediaResponse的实例，与请求对象对应
            DeleteMediaResponse resp = client.DeleteMedia(req);
            // 输出json格式的字符串回包
            System.out.println(DeleteMediaResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }
}
