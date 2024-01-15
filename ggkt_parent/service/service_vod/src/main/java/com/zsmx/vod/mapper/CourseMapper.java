package com.zsmx.vod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsmx.model.vod.Course;
import com.zsmx.vo.vod.CoursePublishVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zsmx
 * @since 2024-01-08
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    //根据id获取课程发布信息
    CoursePublishVo selectCoursePublishVoById(Long id);
}
