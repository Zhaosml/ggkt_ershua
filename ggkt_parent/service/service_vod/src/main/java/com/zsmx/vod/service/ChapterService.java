package com.zsmx.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsmx.model.vod.Chapter;
import com.zsmx.vo.vod.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zsmx
 * @since 2024-01-08
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getNestedTreeList(Long courseId);
    //根据课程id删除章节
    //CourseServiceImpl会调用此方法
    void removeChapterByCourseId(Long id);
}
