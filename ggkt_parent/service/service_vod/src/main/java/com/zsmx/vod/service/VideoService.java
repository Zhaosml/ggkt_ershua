package com.zsmx.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsmx.model.vod.Video;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author zsmx
 * @since 2024-01-08
 */
public interface VideoService extends IService<Video> {

    //根据课程id删除小节
    void removeVideoByCourseId(Long id);

    void removeVideoById(Long id);
}
