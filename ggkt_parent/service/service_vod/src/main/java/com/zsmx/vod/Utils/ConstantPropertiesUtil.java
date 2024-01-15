package com.zsmx.vod.Utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 钊思暮想
 * @date 2024/1/6 12:01
 */

/**
 * 常量类，读取配置文件application.properties中的配置
 * 这里设置的常量都是连接cos存储的
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {
    @Value("${tencent.cos.file.region}")
    private String regin;
    @Value("${tencent.cos.file.secretid}")
    private String secretid;
    @Value("${tencent.cos.file.secretkey}")
    private String secretkey;
    @Value("${tencent.video.appid}")
    private String appid;
    @Value("${tencent.cos.file.bucketname}")
    private String bucketName;

    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = regin;
        ACCESS_KEY_ID = secretid;
        ACCESS_KEY_SECRET = secretkey;
        BUCKET_NAME = bucketName;
    }
}
