package com.zsmx.vod.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsmx.model.vod.Course;
import com.zsmx.vo.vod.CourseFormVo;
import com.zsmx.vo.vod.CoursePublishVo;
import com.zsmx.vo.vod.CourseQueryVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zsmx
 * @since 2024-01-08
 */
public interface CourseService extends IService<Course> {
    //分页查询   课程列表   查询
    Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    //增加课程基本信息     增加
    Long saveCourseInfo(CourseFormVo courseFormVo);
    //根据id获取课程信息   修改-获取id
    CourseFormVo getCourseFormVoById(Long id);
    //根据id修改课程信息
    void updateCourseId(CourseFormVo courseFormVo);
    //根据id获取课程最终发布
    CoursePublishVo getCoursePublishVo(Long id);
    //根据id发布课程
    boolean publishCourseById(Long id);

    //删除课程
    void removeCourseById(Long id);
}
