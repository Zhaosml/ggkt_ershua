package com.zsmx.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsmx.model.vod.VideoVisitor;

import java.util.Map;

/**
 * <p>
 * 视频来访者记录表 服务类
 * </p>
 *
 * @author zsmx
 * @since 2024-01-12
 */
public interface VideoVisitorService extends IService<VideoVisitor> {

    Map<String, Object> findCount(Long courseId, String startDate, String endDate);
}
